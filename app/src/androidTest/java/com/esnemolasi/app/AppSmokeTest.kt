package com.esnemolasi.app

import android.graphics.Bitmap
import android.os.Environment
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.lifecycle.Lifecycle
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class AppSmokeTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun offlineOnboardingSessionAndHistoryFlow() {
        rule.waitUntil(15000) { rule.onAllNodesWithTag("onboarding_safety").fetchSemanticsNodes().isNotEmpty() ||
            rule.onAllNodesWithTag("home_start").fetchSemanticsNodes().isNotEmpty() }
        if (rule.onAllNodesWithTag("onboarding_safety").fetchSemanticsNodes().isNotEmpty()) {
            rule.onNodeWithTag("onboarding_safety").performScrollTo().performClick()
            rule.onNodeWithTag("onboarding_continue").performScrollTo().performClick()
        }
        rule.waitUntil(10000) { rule.onAllNodesWithTag("home_start").fetchSemanticsNodes().isNotEmpty() }
        screenshot("01-home")
        rule.onNodeWithTag("nav_routines").performClick()
        rule.onNodeWithTag("routine_card_R01").performScrollTo().assertIsDisplayed()
        screenshot("02-routines")
        rule.onNodeWithTag("routine_card_R01").performClick()
        rule.onNodeWithTag("detail_start").performScrollTo().performClick()
        rule.waitUntil(10000) { rule.onAllNodesWithTag("pause_session").fetchSemanticsNodes().isNotEmpty() }
        rule.waitUntil(20000) { rule.onAllNodesWithText("HAREKET 1 / 6").fetchSemanticsNodes().isNotEmpty() }
        screenshot("03-session-active")
        rule.onNodeWithTag("pause_session").assertIsDisplayed().performClick()
        rule.waitUntil(5000) { rule.onAllNodesWithTag("resume_session").fetchSemanticsNodes().isNotEmpty() }
        screenshot("03-session-paused")
        rule.onNodeWithTag("resume_session").performClick()
        rule.waitUntil(5000) { rule.onAllNodesWithTag("pause_session").fetchSemanticsNodes().isNotEmpty() }
        rule.activityRule.scenario.moveToState(Lifecycle.State.CREATED)
        rule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        rule.waitUntil(5000) { rule.onAllNodesWithTag("resume_session").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("resume_session").performClick()
        rule.onNodeWithTag("skip_step").performClick()
        rule.onNodeWithTag("pause_session").performClick()
        rule.activityRule.scenario.recreate()
        rule.waitUntil(10000) { rule.onAllNodesWithTag("resume_session").fetchSemanticsNodes().isNotEmpty() }
        rule.onNodeWithTag("resume_session").assertIsDisplayed()
        rule.onNodeWithTag("end_session").performClick()
        rule.onNodeWithTag("confirm_end").performClick()
        rule.waitUntil(10000) { rule.onAllNodesWithTag("result_done").fetchSemanticsNodes().isNotEmpty() }
        screenshot("04-result")
        rule.onNodeWithTag("result_done").performScrollTo().performClick()
        rule.onNodeWithTag("open_settings").performClick()
        screenshot("05-settings")
        rule.onNodeWithContentDescription("Koyu tema").performScrollTo().performClick()
        rule.onNodeWithText("Ayarları kaydet").performScrollTo().performClick()
        rule.waitUntil(10000) { rule.onAllNodesWithTag("home_start").fetchSemanticsNodes().isNotEmpty() }
        rule.waitForIdle()
        screenshot("06-dark-home")
    }

    private fun screenshot(name: String) {
        val target = InstrumentationRegistry.getInstrumentation().targetContext
        val dir = target.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        dir.mkdirs()
        val bitmap = rule.onRoot().captureToImage().asAndroidBitmap()
        File(dir, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    }
}
