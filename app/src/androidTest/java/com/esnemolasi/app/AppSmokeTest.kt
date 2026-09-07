package com.esnemolasi.app

import android.graphics.Bitmap
import android.content.ContentValues
import android.provider.MediaStore
import android.os.Build
import java.io.File
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.lifecycle.Lifecycle
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
        val bitmap = rule.onRoot().captureToImage().asAndroidBitmap()
        if (Build.VERSION.SDK_INT < 29) {
            File(target.getExternalFilesDir(null), "$name.png").outputStream().use {
                check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, it))
            }
            return
        }
        // CI uses API 35. Shared media survives the test runner uninstalling the app.
        val resolver = target.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$name.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/EsnemeEvidence")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val uri = checkNotNull(resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values))
        checkNotNull(resolver.openOutputStream(uri)).use {
            check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, it))
        }
        resolver.update(uri, ContentValues().apply { put(MediaStore.Images.Media.IS_PENDING, 0) }, null, null)
    }
}
