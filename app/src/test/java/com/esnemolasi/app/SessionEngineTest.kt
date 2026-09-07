package com.esnemolasi.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionEngineTest {
    private fun engine() = SessionEngine("desk-reset", "session-1")

    @Test fun startsPausedWithoutEarningUnobservedTime() {
        val engine = engine()
        engine.tick(50_000L)
        assertTrue(engine.snapshot().paused)
        assertEquals("preparation", engine.phase)
        assertEquals(0, engine.step)
        assertEquals(10_000L, engine.stepRemainingMs)
        assertEquals(180_000L, engine.totalRemainingMs)
        assertEquals(0L, engine.snapshot().activeMs)
    }

    @Test fun largeTickCrossesAllBoundariesAndDoesNotCountCompletionOverrun() {
        val engine = engine()
        engine.resume(1_000L)
        engine.tick(500_000L)
        val result = engine.snapshot()
        assertTrue(result.finished)
        assertTrue(result.paused)
        assertEquals("completed", result.status)
        assertEquals(180_000L, result.activeMs)
        assertEquals(150_000L, result.movementMs)
        assertEquals(0, result.skippedMoves)
        assertEquals(12, result.segment)
        assertEquals(5_000L, result.segmentElapsedMs)
        assertEquals("finished", engine.phase)
        assertEquals(0L, engine.totalRemainingMs)
    }

    @Test fun exactBoundariesShowUpcomingMovementDuringTransition() {
        val engine = engine()
        engine.resume(0L)
        engine.tick(10_000L)
        assertEquals("movement", engine.phase)
        assertEquals(0, engine.step)
        assertEquals(25_000L, engine.stepRemainingMs)
        engine.tick(35_000L)
        assertEquals("transition", engine.phase)
        assertEquals(1, engine.step)
        assertEquals(3_000L, engine.stepRemainingMs)
        engine.tick(38_000L)
        assertEquals("movement", engine.phase)
        assertEquals(1, engine.step)
        assertEquals(25_000L, engine.stepRemainingMs)
        assertEquals(25_000L, engine.snapshot().movementMs)
    }

    @Test fun delayedTickLandsPartwayThroughTheCorrectMovement() {
        val engine = engine()
        engine.resume(0L)
        engine.tick(96_000L)
        assertEquals("movement", engine.phase)
        assertEquals(3, engine.step)
        assertEquals(7, engine.snapshot().segment)
        assertEquals(2_000L, engine.snapshot().segmentElapsedMs)
        assertEquals(23_000L, engine.stepRemainingMs)
        assertEquals(84_000L, engine.totalRemainingMs)
        assertEquals(96_000L, engine.snapshot().activeMs)
        assertEquals(77_000L, engine.snapshot().movementMs)
    }

    @Test fun pauseAccountsForPendingTimeAndResumeExcludesPauseDuration() {
        val engine = engine()
        engine.resume(1_000L)
        engine.pause(16_000L)
        assertEquals(15_000L, engine.snapshot().activeMs)
        assertEquals(5_000L, engine.snapshot().movementMs)
        assertEquals(20_000L, engine.stepRemainingMs)
        engine.tick(80_000L)
        assertEquals(15_000L, engine.snapshot().activeMs)
        engine.resume(100_000L)
        engine.tick(105_000L)
        assertFalse(engine.snapshot().paused)
        assertEquals(20_000L, engine.snapshot().activeMs)
        assertEquals(10_000L, engine.snapshot().movementMs)
    }

    @Test fun duplicateResumeCannotDiscardElapsedPlayback() {
        val engine = engine()
        engine.resume(0L)
        engine.resume(8_000L)
        engine.tick(10_000L)
        assertEquals(10_000L, engine.snapshot().activeMs)
        assertEquals("movement", engine.phase)
    }

    @Test fun skippingPreparationDoesNotCountTimeOrPreventFullMovementCompletion() {
        val engine = engine()
        engine.skip(99_000L)
        assertTrue(engine.snapshot().paused)
        assertEquals(0, engine.snapshot().skippedMoves)
        assertEquals(0L, engine.snapshot().activeMs)
        assertEquals(170_000L, engine.totalRemainingMs)
        engine.resume(100_000L)
        engine.tick(270_000L)
        assertEquals("completed", engine.snapshot().status)
        assertEquals(170_000L, engine.snapshot().activeMs)
        assertEquals(150_000L, engine.snapshot().movementMs)
    }

    @Test fun skippingLastMovementPreservesPlayedTimeAndEntersClosing() {
        val engine = engine()
        engine.resume(0L)
        engine.tick(160_000L)
        assertEquals("movement", engine.phase)
        assertEquals(5, engine.step)
        engine.skip(160_000L)
        assertEquals("closing", engine.phase)
        assertEquals(5_000L, engine.totalRemainingMs)
        assertEquals(1, engine.snapshot().skippedMoves)
        assertEquals(160_000L, engine.snapshot().activeMs)
        assertEquals(135_000L, engine.snapshot().movementMs)
        engine.tick(165_000L)
        assertEquals("finished_with_skips", engine.snapshot().status)
        assertEquals(165_000L, engine.snapshot().activeMs)
        assertEquals(135_000L, engine.snapshot().movementMs)
    }

    @Test fun skippingTransitionNeverAwardsTimeOrCountsAsSkippedMovement() {
        val engine = engine()
        engine.resume(0L)
        engine.tick(35_000L)
        engine.skip(35_000L)
        assertEquals("movement", engine.phase)
        assertEquals(1, engine.step)
        assertEquals(35_000L, engine.snapshot().activeMs)
        assertEquals(25_000L, engine.snapshot().movementMs)
        assertEquals(0, engine.snapshot().skippedMoves)
        assertEquals(142_000L, engine.totalRemainingMs)
    }

    @Test fun skippingEverySegmentWhilePausedNeverCreatesActivityCredit() {
        val engine = engine()
        repeat(13) { engine.skip(10_000L) }
        val result = engine.snapshot()
        assertTrue(result.finished)
        assertEquals("finished_with_skips", result.status)
        assertEquals(6, result.skippedMoves)
        assertEquals(0L, result.activeMs)
        assertEquals(0L, result.movementMs)
        assertEquals(0L, engine.totalRemainingMs)
    }

    @Test fun backwardsClockDoesNotRegressBaselineAndCountTimeTwice() {
        val engine = engine()
        engine.resume(1_000L)
        engine.tick(5_000L)
        engine.tick(2_000L)
        assertEquals(4_000L, engine.snapshot().activeMs)
        engine.tick(6_000L)
        assertEquals(5_000L, engine.snapshot().activeMs)
        assertEquals(5_000L, engine.stepRemainingMs)
    }

    @Test fun restorationAlwaysPausesAndDoesNotDependOnPreviousClockEpoch() {
        val original = engine()
        original.resume(50_000L)
        original.tick(63_000L)
        val saved = original.snapshot()
        assertFalse(saved.paused)
        val restored = SessionEngine(saved.routineId, saved.id, saved)
        assertTrue(restored.snapshot().paused)
        assertEquals(saved.copy(paused = true), restored.snapshot())
        restored.tick(1_000_000L)
        assertEquals(13_000L, restored.snapshot().activeMs)
        restored.resume(1_000L)
        restored.tick(3_000L)
        assertEquals(15_000L, restored.snapshot().activeMs)
        assertEquals(5_000L, restored.snapshot().movementMs)
    }

    @Test fun completionIsIdempotentAcrossAllActionsAndPersistence() {
        val engine = engine()
        engine.resume(0L)
        engine.tick(180_000L)
        val completed = engine.snapshot()
        engine.tick(1_000_000L)
        engine.resume(1_000_000L)
        engine.pause(1_100_000L)
        engine.skip(1_200_000L)
        engine.end(1_300_000L)
        assertEquals(completed, engine.snapshot())
        val restored = SessionEngine(completed.routineId, completed.id, completed)
        restored.resume(0L)
        restored.tick(200_000L)
        assertEquals(completed, restored.snapshot())
    }

    @Test fun endingEarlyAccountsForPendingPlaybackButNeverAwardsCompletion() {
        val engine = engine()
        engine.resume(0L)
        engine.end(12_000L)
        val ended = engine.snapshot()
        assertEquals("ended_early", ended.status)
        assertTrue(ended.finished)
        assertTrue(ended.paused)
        assertEquals(12_000L, ended.activeMs)
        assertEquals(2_000L, ended.movementMs)
        assertEquals(0L, engine.totalRemainingMs)
        engine.tick(180_000L)
        engine.end(200_000L)
        assertEquals(ended, engine.snapshot())
    }
}
