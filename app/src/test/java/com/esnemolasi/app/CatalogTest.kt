package com.esnemolasi.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogTest {
    @Test fun allTwelvePlannedRoutinesArePresentInOrder() {
        assertEquals((1..12).map { "R" + it.toString().padStart(2, '0') }, Catalog.routines.map { it.id })
        assertEquals(12, Catalog.routines.map { it.id }.distinct().size)
    }

    @Test fun everyRoutineHasSixResolvableMovementBlocksAndUniqueSequence() {
        Catalog.routines.forEach { routine ->
            assertEquals(routine.id, 6, routine.moves.size)
            routine.moves.forEach { movementId ->
                assertTrue("${routine.id} references missing $movementId", Catalog.movements.containsKey(movementId))
            }
        }
        assertEquals(12, Catalog.routines.map { it.moves }.distinct().size)
    }

    @Test fun routineSequencesMatchTheProductPlanIncludingIntentionalRepeatedAnkles() {
        val expected = listOf(
            "H01,H02,H03,H07,H10,H16", "H01,H02,H03,H05,H12,H16",
            "H01,H02,H04,H03,H07,H16", "H01,H07,H08,H09,H02,H16",
            "H01,H02,H06,H05,H07,H16", "H01,H10,H11,H10,H02,H16",
            "H01,H07,H02,H03,H10,H16", "H01,H02,H12,H07,H10,H16",
            "H13,H02,H14,H07,H12,H16", "H13,H14,H15,H02,H03,H16",
            "H01,H05,H03,H08,H10,H16", "H01,H02,H07,H10,H03,H16"
        )
        assertEquals(expected, Catalog.routines.map { it.moves.joinToString(",") })
    }

    @Test fun allMovementTypesHaveIndividualGuidanceAndRemainDrafts() {
        assertEquals(16, Catalog.movements.size)
        assertEquals(Motion.entries.toSet(), Catalog.movements.values.map { it.motion }.toSet())
        Catalog.movements.forEach { (id, movement) ->
            assertEquals(id, movement.id)
            assertTrue(movement.name.isNotBlank())
            assertTrue(movement.cue.isNotBlank())
            assertTrue(movement.detail.isNotBlank())
        }
        assertEquals("draft", Catalog.reviewStatus)
        assertTrue(Catalog.contentNotice.contains("onaylanmadı"))
    }

    @Test fun positionAndDefaultSelectionDoNotSuggestAnUnintendedNeckOrStandingRoutine() {
        assertEquals(listOf("R09", "R10"), Catalog.routines.filter { it.standing }.map { it.id })
        assertEquals("R01", Catalog.routine("obsolete-link").id)
        assertFalse(Catalog.routine("R01").standing)
        assertFalse(Catalog.routine("R01").moves.contains("H04"))
        Catalog.routines.filter { it.standing }.forEach {
            assertEquals("H13", it.moves.first())
            assertFalse(it.moves.any { id -> id in setOf("H01", "H04", "H06", "H08", "H09", "H10", "H11") })
        }
        Catalog.routines.forEach { assertEquals("H16", it.moves.last()) }
    }
}
