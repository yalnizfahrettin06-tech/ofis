package com.esnemolasi.app

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDate

class ReminderScheduler(private val app: EsnemeApplication) {
    private val dao get() = app.database.dao()
    private val alarm get() = app.getSystemService(AlarmManager::class.java)
    private val manager get() = app.getSystemService(NotificationManager::class.java)
    private val lock = Mutex()

    fun channel() {
        val channel = NotificationChannel(CHANNEL, "Mola hatırlatmaları", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "Çalışma saatlerinde nazik ve yaklaşık mola önerileri"
        channel.setSound(null, null)
        channel.enableVibration(false)
        manager.createNotificationChannel(channel)
    }
    fun allowed(): Boolean {
        channel()
        return (Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(app, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) &&
            manager.areNotificationsEnabled() && manager.getNotificationChannel(CHANNEL)?.importance != NotificationManager.IMPORTANCE_NONE
    }
    private fun pending(generation: Long): PendingIntent = PendingIntent.getBroadcast(app, 100,
        Intent(app, ReminderReceiver::class.java).setAction(TICK).putExtra("generation", generation),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    private suspend fun current(): ReminderLedger {
        val value = dao.ledger() ?: ReminderLedger()
        val today = LocalDate.now().toString()
        return if (value.date == today) value else value.copy(date = today, normalCount = 0, snoozeCount = 0)
    }
    private suspend fun schedule(value: ReminderLedger, s: Settings, reset: Boolean): Long {
        alarm.cancel(pending(value.generation))
        val now = System.currentTimeMillis()
        if (!s.reminders || !allowed()) {
            manager.cancel(NOTIFICATION)
            dao.ledger(value.copy(generation = value.generation + 1, nextAt = 0, notificationGeneration = 0))
            return 0
        }
        val retain = !reset && value.nextAt > now && value.nextAt >= value.blockUntil &&
            ReminderPolicy.eligible(value.nextAt, s) && LocalDate.now().toString() != value.mutedDate
        val next = if (retain) value.nextAt else ReminderPolicy.next(now, s, value.lastEvent,
            value.blockUntil, value.mutedDate, value.normalCount, value.date)
        val nextValue = value.copy(generation = value.generation + 1, nextAt = next, isSnooze = if(retain) value.isSnooze else false)
        dao.ledger(nextValue)
        if (next > 0) {
            if (nextValue.isSnooze) alarm.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next, pending(nextValue.generation))
            else alarm.setWindow(AlarmManager.RTC_WAKEUP, next, 15 * 60_000L, pending(nextValue.generation))
        }
        return next
    }
    suspend fun reconcile(reset: Boolean = false): Long = lock.withLock { schedule(current(), app.preferences.get(), reset) }
    suspend fun saveSettings(settings: Settings): Long = lock.withLock {
        val previous = app.preferences.get()
        app.preferences.set(settings)
        manager.cancel(NOTIFICATION)
        val value = current()
        schedule(value.copy(notificationGeneration = 0,
            lastEvent = if (!previous.reminders && settings.reminders) System.currentTimeMillis() else value.lastEvent), settings, true)
    }
    suspend fun sessionStarted() = lock.withLock {
        manager.cancel(NOTIFICATION)
        schedule(current().copy(blockUntil = System.currentTimeMillis() + 30 * 60_000L, notificationGeneration = 0), app.preferences.get(), true)
    }
    suspend fun heartbeat() = lock.withLock {
        dao.ledger(current().copy(blockUntil = System.currentTimeMillis() + 30 * 60_000L))
    }
    suspend fun sessionEnded() = lock.withLock {
        schedule(current().copy(blockUntil = 0, lastEvent = System.currentTimeMillis()), app.preferences.get(), true)
    }
    suspend fun muteToday() = lock.withLock {
        manager.cancel(NOTIFICATION)
        schedule(current().copy(mutedDate = LocalDate.now().toString(), notificationGeneration = 0), app.preferences.get(), true)
    }
    suspend fun reset() = lock.withLock {
        alarm.cancel(pending(0))
        manager.cancel(NOTIFICATION)
        dao.ledger(ReminderLedger(generation = (dao.ledger()?.generation ?: 0) + 1))
    }
    suspend fun onEvent(action: String?, generation: Long) = lock.withLock {
        var value = current()
        val s = app.preferences.get()
        val now = System.currentTimeMillis()
        if (action == SNOOZE || action == MUTE) {
            if (generation == 0L || generation != value.notificationGeneration) return@withLock
            manager.cancel(NOTIFICATION)
            value = value.copy(notificationGeneration = 0)
            if (action == MUTE) {
                schedule(value.copy(mutedDate = LocalDate.now().toString()), s, true)
            } else if (value.snoozeCount < 3 && s.reminders && allowed()) {
                val next = ReminderPolicy.next(now, s, blockUntil = value.blockUntil, mutedDate = value.mutedDate, snooze = true)
                value = value.copy(generation = value.generation + 1, nextAt = next, isSnooze = true)
                dao.ledger(value)
                alarm.cancel(pending(value.generation))
                if (next > 0) alarm.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next, pending(value.generation))
            } else schedule(value, s, true)
            return@withLock
        }
        if (action != TICK || generation != value.generation || value.nextAt == 0L) return@withLock
        if (now < value.nextAt) { schedule(value, s, false); return@withLock }
        val valid = s.reminders && allowed() && ReminderPolicy.eligible(now, s) && value.mutedDate != LocalDate.now().toString() &&
            now >= value.blockUntil && now - value.nextAt <= 30 * 60_000L &&
            (if(value.isSnooze) value.snoozeCount < 3 else value.normalCount < 6)
        val post = valid && !app.foreground
        value = value.copy(nextAt = 0, lastEvent = now,
            normalCount = value.normalCount + if(post && !value.isSnooze) 1 else 0,
            snoozeCount = value.snoozeCount + if(post && value.isSnooze) 1 else 0,
            notificationGeneration = if(post) generation else 0)
        dao.ledger(value)
        if (post) {
            val start = PendingIntent.getActivity(app, 200, Intent(app, MainActivity::class.java)
                .setAction("com.esnemolasi.START").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            fun actionIntent(action: String, request: Int) = PendingIntent.getBroadcast(app, request,
                Intent(app, ReminderReceiver::class.java).setAction(action).putExtra("generation", generation),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val notification = NotificationCompat.Builder(app, CHANNEL).setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Kısa bir mola?").setContentText("Uygunsan 3 dakikalık hareket molan hazır.")
                .setContentIntent(start).setAutoCancel(true).setOnlyAlertOnce(true)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE).setTimeoutAfter(30 * 60_000L)
                .addAction(0, "Başla", start).addAction(0, "15 dk ertele", actionIntent(SNOOZE, 201))
                .addAction(0, "Bugün sessize al", actionIntent(MUTE, 202)).build()
            try { manager.notify(NOTIFICATION, notification) } catch (_: SecurityException) { }
        }
        schedule(value, s, true)
    }
    companion object {
        const val CHANNEL = "breaks_v1"
        const val NOTIFICATION = 200
        const val TICK = "com.esnemolasi.TICK"
        const val SNOOZE = "com.esnemolasi.SNOOZE"
        const val MUTE = "com.esnemolasi.MUTE"
    }
}

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val result = goAsync()
        val app = context.applicationContext as EsnemeApplication
        app.background.launch {
            try { app.reminders.onEvent(intent.action, intent.getLongExtra("generation", 0)) }
            finally { result.finish() }
        }
    }
}

class RescheduleReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val result = goAsync()
        val app = context.applicationContext as EsnemeApplication
        app.background.launch {
            try {
                val clockChanged = intent.action == Intent.ACTION_TIME_CHANGED || intent.action == Intent.ACTION_TIMEZONE_CHANGED
                app.reminders.reconcile(clockChanged)
            } finally { result.finish() }
        }
    }
}
