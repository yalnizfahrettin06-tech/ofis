package com.esnemolasi.app

import android.app.Application
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application as EsnemeApplication
    private val dao = app.database.dao()
    private val lock = Mutex()
    private val mutable = MutableStateFlow(AppUiState())
    val state: StateFlow<AppUiState> = mutable.asStateFlow()
    private var engine: SessionEngine? = null
    private var checkpointAt = 0L
    private var startRequested = false

    init {
        action {
            dao.prune(System.currentTimeMillis() - 90L * 24 * 60 * 60 * 1000)
            val saved = dao.checkpoint()
            if (saved != null) {
                try {
                    val snapshot = decodeCheckpoint(saved.payload)
                    if (Catalog.routines.none { it.id == snapshot.routineId }) {
                        dao.clearCheckpoint()
                    } else {
                        engine = SessionEngine(snapshot.routineId, snapshot.id, snapshot)
                        if (snapshot.finished || System.currentTimeMillis() - saved.savedAt > 30 * 60_000L || saved.savedAt > System.currentTimeMillis()) {
                            engine?.end(SystemClock.elapsedRealtime())
                            finish(false)
                        }
                    }
                } catch (e: CancellationException) { throw e }
                catch (_: Exception) {
                    engine = null
                    dao.clearCheckpoint()
                    mutable.update { it.copy(error = "Önceki mola geri yüklenemedi. Yeni bir mola başlatabilirsin.") }
                }
            }
            updatePersisted()
            publish()
            mutable.update { it.copy(ready = true) }
            maybeStartRequested()
        }
        viewModelScope.launch {
            while (isActive) {
                delay(100)
                try {
                    lock.withLock {
                        val active = engine ?: return@withLock
                        val was = active.snapshot().segment
                        active.tick(SystemClock.elapsedRealtime())
                        if (active.snapshot().finished) finish(true) else {
                            publish()
                            val now = SystemClock.elapsedRealtime()
                            if (was != active.snapshot().segment) pulse()
                            if (!active.snapshot().paused && now - checkpointAt >= 5000) saveCheckpoint()
                        }
                    }
                } catch (e: CancellationException) { throw e }
                catch (_: Exception) {
                    engine?.pause(SystemClock.elapsedRealtime())
                    publish()
                    mutable.update { it.copy(error = "Mola kaydedilemedi. Depolama alanını kontrol edip yeniden dene.") }
                }
            }
        }
    }

    private fun action(block: suspend () -> Unit) {
        viewModelScope.launch {
            try { lock.withLock { block() } }
            catch (e: CancellationException) { throw e }
            catch (_: Exception) { mutable.update { it.copy(ready = true, error = "İşlem tamamlanamadı. Depolama alanını kontrol edip yeniden dene.") } }
        }
    }

    fun refresh() = action {
        updatePersisted()
        maybeStartRequested()
    }
    private suspend fun updatePersisted() {
        val settings = app.preferences.get()
        val next = app.reminders.reconcile()
        val history = dao.history()
        val ledger = dao.ledger()
        mutable.update { it.copy(settings = settings, history = history, nextReminder = next,
            notificationAllowed = app.reminders.allowed(), mutedToday = ledger?.mutedDate == LocalDate.now().toString()) }
    }
    fun requestNotificationStart() = action {
        startRequested = true
        maybeStartRequested()
    }
    private suspend fun maybeStartRequested() {
        if (!startRequested || !state.value.ready || !state.value.settings.safetyAccepted) return
        startRequested = false
        if (engine != null) return
        start(Catalog.routines[state.value.history.count { it.status == "completed" } % Catalog.routines.size])
    }
    fun startRoutine(routine: Routine) = action { start(routine) }
    private suspend fun start(routine: Routine) {
        if (engine != null || !state.value.settings.safetyAccepted) return
        require(Catalog.routines.any { it.id == routine.id })
        val created = SessionEngine(routine.id, UUID.randomUUID().toString())
        engine = created
        mutable.update { it.copy(result = null) }
        try {
            app.reminders.sessionStarted()
            saveCheckpoint()
            if (app.foreground) created.resume(SystemClock.elapsedRealtime())
            publish()
        } catch (e: Exception) { engine = null; throw e }
    }
    fun pause() = action {
        engine?.pause(SystemClock.elapsedRealtime())
        if (engine?.snapshot()?.finished == true) finish(true) else { publish(); saveCheckpoint() }
    }
    fun onHidden() {
        engine?.pause(SystemClock.elapsedRealtime())
        publish()
        action {
            engine?.pause(SystemClock.elapsedRealtime())
            if (engine?.snapshot()?.finished == true) finish(true) else { publish(); saveCheckpoint() }
        }
    }
    fun resume() = action {
        if (app.foreground) engine?.resume(SystemClock.elapsedRealtime())
        saveCheckpoint()
        publish()
    }
    fun skip() = action {
        engine?.skip(SystemClock.elapsedRealtime())
        if (engine?.snapshot()?.finished == true) finish(true) else { saveCheckpoint(); publish(); pulse() }
    }
    fun endSession() = action {
        engine?.end(SystemClock.elapsedRealtime())
        finish(true)
    }
    fun clearResult() { mutable.update { it.copy(result = null) } }
    fun dismissError() { mutable.update { it.copy(error = null) } }

    private suspend fun saveCheckpoint() {
        val active = engine ?: return
        val snapshot = active.snapshot()
        dao.checkpoint(Checkpoint(payload = snapshot.toJson(), savedAt = System.currentTimeMillis()))
        checkpointAt = SystemClock.elapsedRealtime()
        app.reminders.heartbeat()
    }
    private fun publish() {
        val active = engine
        if (active == null) { mutable.update { it.copy(session = null) }; return }
        val snapshot = active.snapshot()
        val routine = Catalog.routine(active.routineId)
        val movement = Catalog.movements[routine.moves[active.step]]
        val cue = when(active.phase) {
            "preparation" -> if(routine.standing) "Sabit bir desteğin yakınında, rahatça yerleş. Telefonu görebileceğin bir yere bırak."
                else "Sabit, tekerleksiz bir sandalyeye rahatça otur. Telefonu görebileceğin bir yere bırak."
            "transition" -> "Sıradaki harekete yavaşça hazırlan. Acele etmene gerek yok."
            "closing" -> "Hareketi bırak, rahatça dinlen. Molan birazdan tamamlanacak."
            else -> movement?.cue ?: "Rahat hissettiğin aralıkta kal."
        }
        mutable.update { it.copy(session = SessionUi(routine, active.phase, active.step,
            if(active.phase in setOf("movement", "transition")) movement else null,
            cue, active.stepRemainingMs, active.totalRemainingMs, snapshot.segmentElapsedMs, snapshot.activeMs, snapshot.paused)) }
    }
    private suspend fun finish(show: Boolean) {
        val snapshot = engine?.snapshot() ?: return
        if (!snapshot.finished) return
        val routine = Catalog.routine(snapshot.routineId)
        val now = System.currentTimeMillis()
        val record = SessionRecord(snapshot.id, routine.id, routine.title, now, snapshot.activeMs,
            snapshot.movementMs, snapshot.skippedMoves, snapshot.status,
            Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault()).toLocalDate().toString())
        app.database.withTransaction { dao.insertSession(record); dao.clearCheckpoint() }
        engine = null
        mutable.update { it.copy(session = null, result = if(show) record else it.result) }
        app.reminders.sessionEnded()
        if (show) pulse()
        updatePersisted()
    }
    fun saveSettings(settings: Settings) = action {
        if (!settings.valid()) { mutable.update { it.copy(error = "Çalışma günlerini, saatleri ve sessiz aralığı kontrol et.") }; return@action }
        app.reminders.saveSettings(settings.copy(safetyAccepted = state.value.settings.safetyAccepted))
        updatePersisted()
    }
    fun acceptSafety() = action {
        app.preferences.set(state.value.settings.copy(safetyAccepted = true))
        updatePersisted()
        maybeStartRequested()
    }
    fun muteToday() = action { app.reminders.muteToday(); updatePersisted() }
    fun clearHistory() = action { dao.clearHistory(); updatePersisted() }
    fun resetAll() = action {
        engine = null
        app.preferences.set(Settings())
        app.reminders.reset()
        app.database.withTransaction { dao.clearHistory(); dao.clearCheckpoint() }
        mutable.value = AppUiState(ready = true, notificationAllowed = app.reminders.allowed())
        startRequested = false
    }
    private fun pulse() {
        if (!state.value.settings.haptic || !app.foreground) return
        val vibrator = app.getSystemService(Vibrator::class.java)
        if (vibrator?.hasVibrator() == true) vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}
