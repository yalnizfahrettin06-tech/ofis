package com.esnemolasi.app

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.max

object ReminderPolicy {
    fun eligible(at: Long, settings: Settings, zone: ZoneId = ZoneId.systemDefault()): Boolean {
        val t = Instant.ofEpochMilli(at).atZone(zone)
        val minute = t.hour * 60 + t.minute
        return t.dayOfWeek.value in settings.days && minute >= settings.startMinute && minute < settings.endMinute &&
            (!settings.quietEnabled || minute !in settings.quietStart until settings.quietEnd)
    }

    fun next(now: Long, settings: Settings, lastEvent: Long = 0, blockUntil: Long = 0,
             mutedDate: String = "", count: Int = 0, countDate: String = "",
             snooze: Boolean = false, zone: ZoneId = ZoneId.systemDefault()): Long {
        if (!settings.reminders || !settings.valid()) return 0
        val today = Instant.ofEpochMilli(now).atZone(zone).toLocalDate()
        val lowerBound = max(now + 1000, max(blockUntil,
            if (snooze) now + 15 * 60_000L else lastEvent + settings.intervalMinutes * 60_000L))
        for (offset in 0L..14L) {
            val date = today.plusDays(offset)
            if (date.dayOfWeek.value !in settings.days || date.toString() == mutedDate) continue
            if (!snooze && date.toString() == countDate && count >= 6) continue
            val starts = atMinute(date, settings.startMinute, zone)
            val ends = atMinute(date, settings.endMinute, zone)
            val start = starts.minOrNull() ?: continue
            val end = ends.maxOrNull() ?: continue
            val floor = max(lowerBound, start + if (snooze) 0 else settings.intervalMinutes * 60_000L)
            if (floor >= end) continue

            // During a fall-back overlap, a local boundary occurs twice. Also,
            // the offset transition itself can re-enter a working/quiet window.
            // Eligibility changes only at those boundaries, so evaluate each
            // candidate in instant order instead of dropping the whole day.
            val candidates = mutableListOf(floor)
            candidates.addAll(starts)
            candidates.addAll(ends)
            if (settings.quietEnabled) {
                candidates.addAll(atMinute(date, settings.quietStart, zone))
                candidates.addAll(atMinute(date, settings.quietEnd, zone))
            }
            var transition = zone.rules.nextTransition(Instant.ofEpochMilli(start).minusNanos(1))
            while (transition != null && transition.instant.toEpochMilli() < end) {
                candidates.add(transition.instant.toEpochMilli())
                transition = zone.rules.nextTransition(transition.instant.plusNanos(1))
            }
            candidates.asSequence().filter { it >= floor && it < end }
                .sorted().firstOrNull { eligible(it, settings, zone) }?.let { return it }
        }
        return 0
    }

    private fun atMinute(date: LocalDate, minute: Int, zone: ZoneId): List<Long> {
        val local = date.atStartOfDay().plusMinutes(minute.toLong())
        val offsets = zone.rules.getValidOffsets(local)
        return if (offsets.isEmpty()) {
            // A missing wall-clock time resolves to the first real instant
            // after the gap, not that instant plus the missing minute offset.
            listOf(zone.rules.getTransition(local).instant.toEpochMilli())
        } else offsets.map { local.toInstant(it).toEpochMilli() }
    }
}
