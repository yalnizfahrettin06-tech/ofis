package com.esnemolasi.app

import android.Manifest
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings as AndroidSettings
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.room.Room
import kotlinx.coroutines.*

class EsnemeApplication : Application() {
    val database: LocalDatabase by lazy {
        Room.databaseBuilder(this, LocalDatabase::class.java, "esneme.db").build()
    }
    val preferences by lazy { Preferences(this) }
    val reminders by lazy { ReminderScheduler(this) }
    @Volatile var foreground = false
    val background = CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
        android.util.Log.w("EsnemeMolasi", "A background reminder could not be processed; reconcile on next launch.")
    })
}

class MainActivity : ComponentActivity() {
    private val model: AppViewModel by viewModels()
    private val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { model.refresh() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by model.state.collectAsState()
            val playing = state.session?.paused == false
            DisposableEffect(playing) {
                if (playing) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                else window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                onDispose { window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) }
            }
            AppUi(model, onRequestNotifications = {
                if (Build.VERSION.SDK_INT >= 33) permission.launch(Manifest.permission.POST_NOTIFICATIONS)
                else openNotificationSettings()
            }, onOpenNotificationSettings = ::openNotificationSettings)
        }
        if (intent.action == "com.esnemolasi.START") {
            intent.action = null
            model.requestNotificationStart()
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.action == "com.esnemolasi.START") {
            intent.action = null
            model.requestNotificationStart()
        }
    }
    override fun onStart() {
        super.onStart()
        (application as EsnemeApplication).foreground = true
        model.refresh()
    }
    override fun onStop() {
        (application as EsnemeApplication).foreground = false
        if (!isChangingConfigurations) model.onHidden()
        super.onStop()
    }
    private fun openNotificationSettings() {
        startActivity(Intent(AndroidSettings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(AndroidSettings.EXTRA_APP_PACKAGE, packageName))
    }
}
