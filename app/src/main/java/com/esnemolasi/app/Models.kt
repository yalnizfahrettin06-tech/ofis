package com.esnemolasi.app

data class Settings(
    val safetyAccepted: Boolean = false,
    val reminders: Boolean = false,
    val days: Set<Int> = setOf(1, 2, 3, 4, 5),
    val startMinute: Int = 540,
    val endMinute: Int = 1080,
    val intervalMinutes: Int = 60,
    val quietEnabled: Boolean = true,
    val quietStart: Int = 720,
    val quietEnd: Int = 780,
    val theme: String = "system",
    val reducedMotion: Boolean = false,
    val haptic: Boolean = false
) {
    fun valid(): Boolean = days.isNotEmpty() && days.all { it in 1..7 } &&
        startMinute in 0..1439 && endMinute in 1..1440 && startMinute < endMinute &&
        intervalMinutes in setOf(45, 60, 90) &&
        (!quietEnabled || (quietStart in 0..1439 && quietEnd in 1..1440 && quietStart < quietEnd &&
            !(quietStart <= startMinute && quietEnd >= endMinute)))
}

data class SessionUi(
    val routine: Routine,
    val phase: String,
    val step: Int,
    val movement: Movement?,
    val cue: String,
    val stepRemainingMs: Long,
    val totalRemainingMs: Long,
    val elapsedInStepMs: Long,
    val activeMs: Long,
    val paused: Boolean
)

data class AppUiState(
    val ready: Boolean = false,
    val settings: Settings = Settings(),
    val history: List<SessionRecord> = emptyList(),
    val session: SessionUi? = null,
    val result: SessionRecord? = null,
    val notificationAllowed: Boolean = false,
    val nextReminder: Long = 0L,
    val mutedToday: Boolean = false,
    val error: String? = null
)
