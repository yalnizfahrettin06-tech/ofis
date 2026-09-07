package com.esnemolasi.app

import java.time.OffsetDateTime
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReminderPolicyTest {
    private val istanbul = ZoneId.of("Europe/Istanbul")
    private val berlin = ZoneId.of("Europe/Berlin")
    private val weekdayPlan = Settings(
        reminders = true,
        days = setOf(1, 2, 3, 4, 5),
        startMinute = 9 * 60,
        endMinute = 18 * 60,
        intervalMinutes = 60,
        quietEnabled = true,
        quietStart = 12 * 60,
        quietEnd = 13 * 60,
    )

    private fun time(iso: String): Long = OffsetDateTime.parse(iso).toInstant().toEpochMilli()

    @Test fun beforeWorkWaitsForOneFullWorkingInterval() {
        val actual = ReminderPolicy.next(time("2026-09-08T08:30:00+03:00"), weekdayPlan, zone = istanbul)
        assertEquals(time("2026-09-08T10:00:00+03:00"), actual)
    }

    @Test fun recentBreakDefersReminderUntilTheSelectedIntervalElapses() {
        val actual = ReminderPolicy.next(
            time("2026-09-08T10:05:00+03:00"), weekdayPlan,
            lastEvent = time("2026-09-08T09:30:00+03:00"), zone = istanbul,
        )
        assertEquals(time("2026-09-08T10:30:00+03:00"), actual)
    }

    @Test fun activeSessionBlockOverridesAnOtherwiseDueReminder() {
        val actual = ReminderPolicy.next(
            time("2026-09-08T10:05:00+03:00"), weekdayPlan,
            lastEvent = time("2026-09-08T09:30:00+03:00"),
            blockUntil = time("2026-09-08T11:20:00+03:00"), zone = istanbul,
        )
        assertEquals(time("2026-09-08T11:20:00+03:00"), actual)
    }

    @Test fun lunchQuietTimeDefersReminderToItsEnd() {
        val actual = ReminderPolicy.next(
            time("2026-09-08T11:30:00+03:00"), weekdayPlan,
            lastEvent = time("2026-09-08T11:00:00+03:00"), zone = istanbul,
        )
        assertEquals(time("2026-09-08T13:00:00+03:00"), actual)
        assertFalse(ReminderPolicy.eligible(time("2026-09-08T12:00:00+03:00"), weekdayPlan, istanbul))
        assertTrue(ReminderPolicy.eligible(time("2026-09-08T13:00:00+03:00"), weekdayPlan, istanbul))
    }

    @Test fun fridayReminderThatWouldLandAtClosingMovesToMonday() {
        val actual = ReminderPolicy.next(
            time("2026-09-11T17:30:00+03:00"), weekdayPlan,
            lastEvent = time("2026-09-11T17:00:00+03:00"), zone = istanbul,
        )
        assertEquals(time("2026-09-14T10:00:00+03:00"), actual)
        assertFalse(ReminderPolicy.eligible(time("2026-09-11T18:00:00+03:00"), weekdayPlan, istanbul))
        assertFalse(ReminderPolicy.eligible(time("2026-09-12T10:00:00+03:00"), weekdayPlan, istanbul))
    }

    @Test fun mutedTuesdayResumesWednesdayAfterTheFirstInterval() {
        val actual = ReminderPolicy.next(
            time("2026-09-08T11:00:00+03:00"), weekdayPlan,
            mutedDate = "2026-09-08", zone = istanbul,
        )
        assertEquals(time("2026-09-09T10:00:00+03:00"), actual)
    }

    @Test fun sixNormalNotificationsExhaustTodayButNotTomorrow() {
        val actual = ReminderPolicy.next(
            time("2026-09-08T11:00:00+03:00"), weekdayPlan,
            count = 6, countDate = "2026-09-08", zone = istanbul,
        )
        assertEquals(time("2026-09-09T10:00:00+03:00"), actual)
    }

    @Test fun previousDaysLimitDoesNotSuppressTodaysReminder() {
        val actual = ReminderPolicy.next(
            time("2026-09-08T10:05:00+03:00"), weekdayPlan,
            lastEvent = time("2026-09-08T09:30:00+03:00"),
            count = 6, countDate = "2026-09-07", zone = istanbul,
        )
        assertEquals(time("2026-09-08T10:30:00+03:00"), actual)
    }

    @Test fun snoozeUsesFifteenMinutesAndStillRespectsLunchQuietTime() {
        val actual = ReminderPolicy.next(
            time("2026-09-08T11:55:00+03:00"), weekdayPlan, snooze = true, zone = istanbul,
        )
        assertEquals(time("2026-09-08T13:00:00+03:00"), actual)
    }

    @Test fun closingTimeSnoozeWaitsUntilNextWorkdayOpening() {
        val actual = ReminderPolicy.next(
            time("2026-09-11T17:50:00+03:00"), weekdayPlan, snooze = true, zone = istanbul,
        )
        assertEquals(time("2026-09-14T09:00:00+03:00"), actual)
    }

    @Test fun changedTimezoneUsesNewLocalWorkingHours() {
        val now = time("2026-09-08T06:30:00Z")
        val inIstanbul = ReminderPolicy.next(now, weekdayPlan, zone = istanbul)
        val inBerlin = ReminderPolicy.next(now, weekdayPlan, zone = berlin)
        assertEquals(time("2026-09-08T07:00:00Z"), inIstanbul)
        assertEquals(time("2026-09-08T08:00:00Z"), inBerlin)
    }

    @Test fun springClockChangeKeepsSundayOpeningAtLocalTen() {
        val plan = weekdayPlan.copy(days = setOf(7))
        val actual = ReminderPolicy.next(time("2026-03-28T20:00:00Z"), plan, zone = berlin)
        assertEquals(time("2026-03-29T10:00:00+02:00"), actual)
    }

    @Test fun autumnRepeatedQuietHourEndsDuringTheSameWorkday() {
        val plan = weekdayPlan.copy(
            days = setOf(7), startMinute = 60, endMinute = 240,
            quietStart = 120, quietEnd = 150,
        )
        // This is the SECOND 02:10, after Berlin has returned to UTC+01.
        val actual = ReminderPolicy.next(time("2026-10-25T02:10:00+01:00"), plan, zone = berlin)
        assertEquals(time("2026-10-25T02:30:00+01:00"), actual)
    }

    @Test fun autumnClockRollbackCanReopenAWindowThatAlreadyClosedOnce() {
        val plan = weekdayPlan.copy(
            days = setOf(7), startMinute = 60, endMinute = 150, quietEnabled = false,
        )
        val actual = ReminderPolicy.next(time("2026-10-25T02:45:00+02:00"), plan, zone = berlin)
        assertEquals(time("2026-10-25T02:00:00+01:00"), actual)
    }

    @Test fun nonexistentSpringQuietEndResolvesToTheClockJumpWithoutExtraDelay() {
        val plan = weekdayPlan.copy(
            days = setOf(7), startMinute = 60, endMinute = 240,
            quietStart = 90, quietEnd = 150,
        )
        val actual = ReminderPolicy.next(time("2026-03-29T01:40:00+01:00"), plan, zone = berlin)
        assertEquals(time("2026-03-29T03:00:00+02:00"), actual)
    }

    @Test fun disabledOrInvalidPlansNeverScheduleNotifications() {
        val invalidPlans = listOf(
            weekdayPlan.copy(reminders = false),
            weekdayPlan.copy(days = emptySet()),
            weekdayPlan.copy(days = setOf(0, 8)),
            weekdayPlan.copy(startMinute = 1080, endMinute = 540),
            weekdayPlan.copy(startMinute = -1),
            weekdayPlan.copy(endMinute = 1441),
            weekdayPlan.copy(intervalMinutes = 15),
            weekdayPlan.copy(quietStart = 13 * 60, quietEnd = 12 * 60),
            weekdayPlan.copy(quietStart = 0, quietEnd = 1440),
        )
        invalidPlans.forEach { plan ->
            assertEquals(0L, ReminderPolicy.next(time("2026-09-08T10:00:00+03:00"), plan, zone = istanbul))
        }
    }
}
