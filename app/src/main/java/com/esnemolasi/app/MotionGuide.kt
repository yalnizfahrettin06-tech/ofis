package com.esnemolasi.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min

/**
 * An original, schematic movement guide for this prototype, pending expert review.
 * Time is supplied by the session: pausing the session freezes the pose exactly.
 * There are deliberately no frame clocks, effects, or independent animation loops here.
 */
@Composable
fun MotionGuide(
    movement: Movement?,
    standing: Boolean,
    elapsedMs: Long,
    playing: Boolean,
    reducedMotion: Boolean,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val dark = scheme.surface.luminance() < 0.45f
    val palette = GuidePalette(
        shirt = if (dark) Color(0xFF8DC8B2) else Color(0xFF5F9D85),
        trousers = if (dark) Color(0xFFB6C7C0) else Color(0xFF435A52),
        skin = if (dark) Color(0xFFE0C4AC) else Color(0xFFC39B7D),
        ink = scheme.onSurface,
        furniture = scheme.onSurfaceVariant.copy(alpha = if (dark) 0.30f else 0.20f),
        halo = scheme.primary.copy(alpha = if (dark) 0.13f else 0.08f),
        surface = scheme.surface
    )
    val description = buildString {
        append(movement?.name ?: if (standing) "Ayakta başlangıç duruşu" else "Oturarak başlangıç duruşu")
        append(if (standing) ". Ayakta gösterim. " else ". Sandalyede oturarak gösterim. ")
        if (reducedMotion) append("Sabit hareket çizimi. ")
        else if (!playing) append("Çizim duraklatıldı. ")
        append(movement?.cue ?: "Rahat bir başlangıç bul.")
    }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.12f)
            .semantics { contentDescription = description }
    ) {
        val drawingScale = min(size.width / 320f, size.height / 280f)
        withTransform({
            translate((size.width - 320f * drawingScale) / 2f, (size.height - 280f * drawingScale) / 2f)
            scale(drawingScale, drawingScale, Offset.Zero)
        }) {
            drawGuide(movement?.motion ?: if (standing) Motion.STAND else Motion.SETTLE,
                standing, elapsedMs.coerceAtLeast(0L), reducedMotion, palette)
        }
    }
}

private data class GuidePalette(
    val shirt: Color,
    val trousers: Color,
    val skin: Color,
    val ink: Color,
    val furniture: Color,
    val halo: Color,
    val surface: Color
)

private fun DrawScope.drawGuide(
    motion: Motion,
    standing: Boolean,
    elapsedMs: Long,
    reducedMotion: Boolean,
    p: GuidePalette
) {
    // A six-second soft excursion returns to neutral; alternating moves use two excursions.
    val cycle = (elapsedMs % 6000L).toFloat() / 6000f
    val amplitude = if (reducedMotion) 0.65f else ((1.0 - cos(cycle * 2.0 * PI)) / 2.0).toFloat()
    val direction = if ((elapsedMs / 6000L) % 2L == 0L) 1f else -1f
    val alternating = if (reducedMotion) 0.65f else amplitude * direction
    val shift = if (motion == Motion.SHIFT) alternating * 6f else 0f
    val heelLift = if (motion == Motion.HEELS) amplitude * 7f else 0f
    val cx = 160f + shift
    val baseShoulder = (if (standing) 83f else 91f) - heelLift
    val hipY = (if (standing) 158f else 166f) - heelLift
    val shoulderLift = if (motion == Motion.SHOULDERS) amplitude * 7f else 0f
    val scapula = if (motion == Motion.SCAPULA) amplitude * 5f else 0f
    val twist = if (motion == Motion.TWIST) alternating * 7f else 0f
    val shoulderY = baseShoulder - shoulderLift
    val ls = Offset(cx - 27f + scapula + twist, shoulderY)
    val rs = Offset(cx + 27f - scapula + twist, shoulderY)
    val head = Offset(cx + twist * 0.45f, (if (standing) 43f else 51f) - heelLift)
    val lh = Offset(cx - 16f, hipY)
    val rh = Offset(cx + 16f, hipY)
    val handMove = motion == Motion.HANDS || motion == Motion.WRISTS || motion == Motion.FOREARMS

    drawCircle(p.halo, 98f, Offset(160f, 138f))
    drawOval(p.furniture.copy(alpha = 0.14f), Offset(89f, 250f), Size(144f, 10f))
    drawLine(p.furniture, Offset(52f, 254f), Offset(305f, 254f), 1.2f, StrokeCap.Round)

    if (!standing) {
        // A fixed four-leg chair is deliberately distinguishable from a wheeled office chair.
        drawRoundRect(p.furniture, Offset(123f, 122f), Size(74f, 54f), CornerRadius(8f))
        drawRoundRect(p.furniture, Offset(118f, 175f), Size(84f, 9f), CornerRadius(4f))
        drawLine(p.furniture, Offset(128f, 182f), Offset(124f, 252f), 5f, StrokeCap.Round)
        drawLine(p.furniture, Offset(192f, 182f), Offset(196f, 252f), 5f, StrokeCap.Round)
    }
    val supported = standing && (motion == Motion.STAND || motion == Motion.SHIFT || motion == Motion.HEELS)
    if (supported) {
        drawRoundRect(p.furniture, Offset(230f, 143f), Size(77f, 8f), CornerRadius(3f))
        drawLine(p.furniture, Offset(241f, 151f), Offset(241f, 251f), 5f, StrokeCap.Round)
        drawLine(p.furniture, Offset(296f, 151f), Offset(296f, 251f), 5f, StrokeCap.Round)
    }

    var lk = Offset(if (standing) 145f + shift * 0.5f else 125f, 198f - heelLift)
    var rk = Offset(if (standing) 175f + shift * 0.5f else 195f, 198f - heelLift)
    var la = Offset(if (standing) 143f else 124f, 244f - heelLift)
    var ra = Offset(if (standing) 177f else 196f, 244f - heelLift)
    if (!standing && motion == Motion.MARCH) {
        val leftLift = if (alternating >= 0f) alternating * 9f else 0f
        val rightLift = if (alternating < 0f) -alternating * 9f else 0f
        lk -= Offset(0f, leftLift)
        la -= Offset(0f, leftLift)
        rk -= Offset(0f, rightLift)
        ra -= Offset(0f, rightLift)
    }
    jointedLimb(lh, lk, la, p.trousers, 17f)
    jointedLimb(rh, rk, ra, p.trousers, 17f)
    val leftToeLift = if (motion == Motion.ANKLES && alternating >= 0f) alternating * 9f else 0f
    val rightToeLift = if (motion == Motion.ANKLES && alternating < 0f) -alternating * 9f else 0f
    val lToe = Offset(la.x - 14f, if (motion == Motion.HEELS) 250f else la.y + 6f - leftToeLift)
    val rToe = Offset(ra.x + 14f, if (motion == Motion.HEELS) 250f else ra.y + 6f - rightToeLift)
    drawLine(p.trousers, la, lToe, 11f, StrokeCap.Round)
    drawLine(p.trousers, ra, rToe, 11f, StrokeCap.Round)

    val hemY = hipY - 1f
    val torso = Path().apply {
        moveTo(ls.x, ls.y)
        quadraticBezierTo(cx - 16f + twist, baseShoulder - 10f, cx - 8f + twist, baseShoulder - 8f)
        lineTo(cx + 8f + twist, baseShoulder - 8f)
        quadraticBezierTo(cx + 16f + twist, baseShoulder - 10f, rs.x, rs.y)
        cubicTo(rs.x + 1f, baseShoulder + 22f, cx + 24f, hemY - 18f, cx + 24f, hemY)
        quadraticBezierTo(cx, hemY + 7f, cx - 24f, hemY)
        cubicTo(cx - 24f, hemY - 18f, ls.x - 1f, baseShoulder + 22f, ls.x, ls.y)
        close()
    }
    drawPath(torso, p.shirt)
    // A curved shirt seam makes torso rotation legible without changing the pelvis.
    if (motion == Motion.TWIST || motion == Motion.SCAPULA) {
        val seam = Path().apply {
            moveTo(cx + twist * 1.5f, baseShoulder + 6f)
            quadraticBezierTo(cx + twist * 2f, baseShoulder + 37f, cx, hemY - 8f)
        }
        drawPath(seam, p.surface.copy(alpha = 0.35f), style = Stroke(1.5f))
    }

    var le = Offset(cx - 41f + scapula, baseShoulder + 36f - shoulderLift)
    var re = Offset(cx + 41f - scapula, baseShoulder + 36f - shoulderLift)
    var lw = Offset(cx - 32f + scapula, hipY - 9f - shoulderLift)
    var rw = Offset(cx + 32f - scapula, hipY - 9f - shoulderLift)
    if (motion == Motion.CHEST) {
        le = Offset(cx - 42f - amplitude * 12f, baseShoulder + 31f - amplitude * 7f)
        re = Offset(cx + 42f + amplitude * 12f, baseShoulder + 31f - amplitude * 7f)
        lw = Offset(cx - 43f - amplitude * 25f, baseShoulder + 52f - amplitude * 18f)
        rw = Offset(cx + 43f + amplitude * 25f, baseShoulder + 52f - amplitude * 18f)
    }
    if (handMove) {
        le = Offset(cx - 42f, baseShoulder + 39f)
        re = Offset(cx + 42f, baseShoulder + 39f)
        lw = Offset(cx - 53f, baseShoulder + 20f)
        rw = Offset(cx + 53f, baseShoulder + 20f)
    }
    if (motion == Motion.REACH) {
        val left = if (alternating >= 0f) alternating else 0f
        val right = if (alternating < 0f) -alternating else 0f
        le = Offset(cx - 40f - 9f * left, baseShoulder + 36f - 14f * left)
        re = Offset(cx + 40f + 9f * right, baseShoulder + 36f - 14f * right)
        lw = Offset(cx - 32f - 37f * left, hipY - 9f - 49f * left)
        rw = Offset(cx + 32f + 37f * right, hipY - 9f - 49f * right)
    }
    if (supported) {
        re = Offset(225f, 116f)
        rw = Offset(260f, 139f)
        if (motion == Motion.HEELS) {
            le = Offset(178f, 124f)
            lw = Offset(237f, 139f)
        }
    }
    jointedLimb(ls, le, lw, p.skin, 12f)
    jointedLimb(rs, re, rw, p.skin, 12f)
    drawLine(p.shirt, ls, ls + (le - ls) * 0.47f, 18f, StrokeCap.Round)
    drawLine(p.shirt, rs, rs + (re - rs) * 0.47f, 18f, StrokeCap.Round)
    val wristAngle = if (motion == Motion.WRISTS) alternating * 0.52f else 0f
    val openness = if (motion == Motion.HANDS) 1f - amplitude * 0.9f else 0.4f
    val palmWidth = if (motion == Motion.FOREARMS) 0.35f + amplitude * 0.65f else 1f
    drawHand(lw, -wristAngle, openness, palmWidth, handMove, p)
    drawHand(rw, wristAngle, openness, palmWidth, handMove, p)

    drawLine(p.skin, Offset(head.x, head.y + 13f), Offset(cx + twist, baseShoulder - 3f), 11f, StrokeCap.Round)
    val headTurn = if (motion == Motion.NECK) alternating else if (motion == Motion.TWIST) alternating * 0.5f else 0f
    drawOval(p.skin, head - Offset(16f, 20f), Size(32f, 39f))
    // Hair cap, a moving nose and visible eye communicate an actual turn, not head tilt.
    val hair = Path().apply {
        moveTo(head.x - 16f, head.y - 1f)
        cubicTo(head.x - 22f, head.y - 28f, head.x + 20f, head.y - 29f, head.x + 16f, head.y - 1f)
        quadraticBezierTo(head.x + 7f, head.y - 15f, head.x - 1f, head.y - 10f)
        quadraticBezierTo(head.x - 7f, head.y - 11f, head.x - 16f, head.y - 1f)
        close()
    }
    drawPath(hair, p.trousers)
    val faceX = head.x + headTurn * 10f
    if (headTurn < 0.7f) drawCircle(p.ink.copy(alpha = 0.72f), 1.2f, Offset(faceX - 5f, head.y + 1f))
    if (headTurn > -0.7f) drawCircle(p.ink.copy(alpha = 0.72f), 1.2f, Offset(faceX + 5f, head.y + 1f))
    drawLine(p.ink.copy(alpha = 0.3f), Offset(faceX, head.y + 3f), Offset(faceX + headTurn * 3f, head.y + 7f), 1.2f, StrokeCap.Round)
    drawLine(p.ink.copy(alpha = 0.35f), Offset(faceX - 3f, head.y + 11f), Offset(faceX + 3f, head.y + 11f), 1.2f, StrokeCap.Round)

    // Static emphasis is informational; it does not introduce a second animation rhythm.
    val accent = p.shirt.copy(alpha = 0.48f)
    when (motion) {
        Motion.NECK -> drawArc(accent, 200f, 140f, false, head - Offset(29f, 31f), Size(58f, 62f), style = Stroke(1.5f, cap = StrokeCap.Round))
        Motion.SHOULDERS, Motion.SCAPULA -> {
            drawCircle(accent, 13f, ls, style = Stroke(1.3f))
            drawCircle(accent, 13f, rs, style = Stroke(1.3f))
        }
        Motion.ANKLES, Motion.HEELS -> {
            drawCircle(accent, 18f, la, style = Stroke(1.3f))
            drawCircle(accent, 18f, ra, style = Stroke(1.3f))
        }
        Motion.MARCH -> {
            drawCircle(accent, 15f, lk, style = Stroke(1.3f))
            drawCircle(accent, 15f, rk, style = Stroke(1.3f))
        }
        Motion.BREATHE -> {
            // Decorative chest contour only: breathing is never prescribed by this cadence.
            drawArc(p.surface.copy(alpha = 0.55f), 210f, 120f, false,
                Offset(cx - 18f, baseShoulder + 16f), Size(36f, 35f), style = Stroke(1.5f, cap = StrokeCap.Round))
        }
        else -> Unit
    }
}

private fun DrawScope.jointedLimb(start: Offset, joint: Offset, end: Offset, color: Color, width: Float) {
    drawLine(color, start, joint, width, StrokeCap.Round)
    drawLine(color, joint, end, width * 0.88f, StrokeCap.Round)
    drawCircle(color, width * 0.5f, joint)
}

private fun DrawScope.drawHand(
    at: Offset,
    rotation: Float,
    openness: Float,
    palmWidth: Float,
    detailed: Boolean,
    p: GuidePalette
) {
    if (!detailed) {
        drawOval(p.skin, at - Offset(5f, 3f), Size(10f, 13f))
        return
    }
    withTransform({ rotate((rotation * 180f / PI).toFloat(), at) }) {
        drawOval(p.skin, at - Offset(5f * palmWidth, 7f), Size(10f * palmWidth, 12f))
        for (finger in 0..3) {
            val spread = (finger - 1.5f) * 3.8f * openness
            val origin = at + Offset((finger - 1.5f) * 2.1f * palmWidth, -4f)
            val end = origin + Offset(spread, -(3f + 7f * openness) + (if (finger == 0 || finger == 3) 2f else 0f))
            drawLine(p.skin, origin, end, 2.3f, StrokeCap.Round)
        }
        drawLine(p.skin, at + Offset(3f * palmWidth, -1f), at + Offset(6f + openness * 4f, -3f - openness * 4f), 2.8f, StrokeCap.Round)
        drawLine(p.ink.copy(alpha = 0.23f), at + Offset(-2f * palmWidth, -2f), at + Offset(2f * palmWidth, 0f), 0.8f, StrokeCap.Round)
    }
}
