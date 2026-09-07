package com.esnemolasi.app

/** A complete persistence record. Restoring a live record always pauses it. */
data class EngineSnapshot(
    val routineId: String,
    val id: String,
    val segment: Int,
    val segmentElapsedMs: Long,
    val activeMs: Long,
    val movementMs: Long,
    val skippedMoves: Int,
    val paused: Boolean,
    val finished: Boolean,
    val status: String,
)

/**
 * A deterministic 180-second routine clock with no Android dependencies.
 *
 * Callers supply milliseconds from the same monotonic clock for every action.
 * Preparation is 10s, six movements are 25s each, five transitions are 3s each,
 * and closing is 5s. Only actual playback contributes to active/movement time.
 *
 * `step` is zero-based: transitions refer to the upcoming movement. Preparation
 * refers to step 0 and closing to step 5. A skip can also dismiss preparation,
 * a transition, or closing; only partially unplayed movements count as skipped.
 */
class SessionEngine(
    val routineId: String,
    val id: String,
    initial: EngineSnapshot? = null,
) {
    private val restored = initial?.takeIf { it.routineId == routineId && it.id == id }
    private var segment = restored?.segment?.coerceIn(0, DURATIONS.lastIndex) ?: 0
    private var segmentElapsedMs = (restored?.segmentElapsedMs ?: 0L)
        .coerceIn(0L, DURATIONS[segment])
    private var activeMs = (restored?.activeMs ?: 0L).coerceIn(0L, TOTAL_MS)
    private var movementMs = (restored?.movementMs ?: 0L).coerceIn(0L, MOVEMENT_MS)
    private var skippedMoves = (restored?.skippedMoves ?: 0).coerceIn(0, 6)
    private var finished = restored?.finished ?: false
    private var paused = true
    private var status = if (finished) {
        restored?.status?.takeIf { it in TERMINAL_STATUSES } ?: completionStatus()
    } else {
        "in_progress"
    }
    private var lastNowMs: Long? = null

    val phase: String
        get() = when {
            finished -> "finished"
            segment == 0 -> "preparation"
            segment == DURATIONS.lastIndex -> "closing"
            isMovement(segment) -> "movement"
            else -> "transition"
        }

    val step: Int
        get() = (segment / 2).coerceIn(0, 5)

    val stepRemainingMs: Long
        get() = if (finished) 0L else DURATIONS[segment] - segmentElapsedMs

    /** Remaining scheduled playback. Skips reduce this without earning time. */
    val totalRemainingMs: Long
        get() = if (finished) 0L else {
            stepRemainingMs + ((segment + 1)..DURATIONS.lastIndex).sumOf { DURATIONS[it] }
        }

    fun snapshot(): EngineSnapshot = EngineSnapshot(
        routineId = routineId,
        id = id,
        segment = segment,
        segmentElapsedMs = segmentElapsedMs,
        activeMs = activeMs,
        movementMs = movementMs,
        skippedMoves = skippedMoves,
        paused = paused,
        finished = finished,
        status = status,
    )

    /** Already-running resume calls must not reset the clock or discard time. */
    fun resume(nowMs: Long) {
        if (finished || !paused) return
        paused = false
        lastNowMs = nowMs
    }

    fun pause(nowMs: Long) {
        if (finished) return
        tick(nowMs)
        paused = true
        lastNowMs = null
    }

    fun tick(nowMs: Long) {
        if (paused || finished) return
        val previous = lastNowMs ?: run {
            lastNowMs = nowMs
            return
        }
        // A stale event must not move the baseline backwards and double-count.
        if (nowMs <= previous) return
        val difference = nowMs - previous
        var elapsed = if (difference < 0L) Long.MAX_VALUE else difference
        lastNowMs = nowMs
        while (elapsed > 0L && !finished) {
            val remaining = DURATIONS[segment] - segmentElapsedMs
            val consumed = minOf(elapsed, remaining)
            segmentElapsedMs += consumed
            activeMs += consumed
            if (isMovement(segment)) movementMs += consumed
            elapsed -= consumed
            if (segmentElapsedMs == DURATIONS[segment]) advance()
        }
    }

    /** Account for playback up to the action, then discard the segment remainder. */
    fun skip(nowMs: Long) {
        if (finished) return
        tick(nowMs)
        if (finished) return
        if (isMovement(segment) && segmentElapsedMs < DURATIONS[segment]) skippedMoves++
        advance()
    }

    fun end(nowMs: Long) {
        if (finished) return
        tick(nowMs)
        if (finished) return
        finished = true
        paused = true
        status = "ended_early"
        lastNowMs = null
    }

    private fun advance() {
        if (segment == DURATIONS.lastIndex) {
            segmentElapsedMs = DURATIONS[segment]
            finished = true
            paused = true
            status = completionStatus()
            lastNowMs = null
        } else {
            segment++
            segmentElapsedMs = 0L
        }
    }

    private fun completionStatus(): String =
        if (skippedMoves == 0 && movementMs == MOVEMENT_MS) "completed" else "finished_with_skips"

    private fun isMovement(index: Int): Boolean = index in 1..11 && index % 2 == 1

    companion object {
        const val TOTAL_MS = 180_000L
        const val MOVEMENT_MS = 150_000L
        private val DURATIONS = longArrayOf(
            10_000L, 25_000L, 3_000L, 25_000L, 3_000L, 25_000L, 3_000L,
            25_000L, 3_000L, 25_000L, 3_000L, 25_000L, 5_000L,
        )
        private val TERMINAL_STATUSES = setOf("completed", "finished_with_skips", "ended_early")
    }
}
