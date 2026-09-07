package com.esnemolasi.app

import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseMigrationTest {
    @Test fun firstPreviewRecordsSurviveUpgrade() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val name = "migration-check.db"
        context.deleteDatabase(name)
        val file = context.getDatabasePath(name)
        file.parentFile?.mkdirs()
        SQLiteDatabase.openOrCreateDatabase(file, null).use { db ->
            db.execSQL("CREATE TABLE sessions (id TEXT NOT NULL PRIMARY KEY, routineId TEXT NOT NULL, title TEXT NOT NULL, endedAt INTEGER NOT NULL, activeMs INTEGER NOT NULL, movementMs INTEGER NOT NULL, skippedMoves INTEGER NOT NULL, status TEXT NOT NULL, localDate TEXT NOT NULL)")
            db.execSQL("CREATE TABLE checkpoint (id INTEGER NOT NULL PRIMARY KEY, payload TEXT NOT NULL, savedAt INTEGER NOT NULL)")
            db.execSQL("CREATE TABLE reminder (id INTEGER NOT NULL PRIMARY KEY, generation INTEGER NOT NULL, nextAt INTEGER NOT NULL, lastEvent INTEGER NOT NULL, date TEXT NOT NULL, normalCount INTEGER NOT NULL, snoozeCount INTEGER NOT NULL, isSnooze INTEGER NOT NULL, mutedDate TEXT NOT NULL, blockUntil INTEGER NOT NULL, notificationGeneration INTEGER NOT NULL)")
            db.execSQL("INSERT INTO sessions VALUES ('first', 'R01', 'İlk mola', 1000, 180000, 150000, 0, 'completed', '2026-09-07')")
            db.execSQL("INSERT INTO checkpoint VALUES (1, '{}', 2000)")
            db.version = 1
        }
        val db = Room.databaseBuilder(context, LocalDatabase::class.java, name).addMigrations(MIGRATION_1_2).build()
        try {
            assertEquals("first", db.dao().history().single().id)
            assertEquals("", db.dao().checkpoint()!!.localDate)
            assertEquals(2000L, db.dao().checkpoint()!!.savedAt)
        } finally { db.close(); context.deleteDatabase(name) }
    }
}
