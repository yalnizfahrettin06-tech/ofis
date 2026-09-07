package com.esnemolasi.app

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject

private val Context.preferenceStore by preferencesDataStore("preferences")

@Entity(tableName = "sessions")
data class SessionRecord(
    @PrimaryKey val id: String,
    val routineId: String,
    val title: String,
    val endedAt: Long,
    val activeMs: Long,
    val movementMs: Long,
    val skippedMoves: Int,
    val status: String,
    val localDate: String
)

@Entity(tableName = "checkpoint")
data class Checkpoint(@PrimaryKey val id: Int = 1, val payload: String, val savedAt: Long,
    @ColumnInfo(defaultValue = "''") val localDate: String = "")

@Entity(tableName = "reminder")
data class ReminderLedger(
    @PrimaryKey val id: Int = 1,
    val generation: Long = 0,
    val nextAt: Long = 0,
    val lastEvent: Long = 0,
    val date: String = "",
    val normalCount: Int = 0,
    val snoozeCount: Int = 0,
    val isSnooze: Boolean = false,
    val mutedDate: String = "",
    val blockUntil: Long = 0,
    val notificationGeneration: Long = 0
)

@Dao
interface LocalDao {
    @Query("SELECT * FROM sessions ORDER BY endedAt DESC LIMIT 300") suspend fun history(): List<SessionRecord>
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun insertSession(record: SessionRecord)
    @Query("DELETE FROM sessions WHERE endedAt < :before") suspend fun prune(before: Long)
    @Query("DELETE FROM sessions") suspend fun clearHistory()
    @Query("SELECT * FROM checkpoint WHERE id = 1") suspend fun checkpoint(): Checkpoint?
    @Upsert suspend fun checkpoint(checkpoint: Checkpoint)
    @Query("DELETE FROM checkpoint") suspend fun clearCheckpoint()
    @Query("SELECT * FROM reminder WHERE id = 1") suspend fun ledger(): ReminderLedger?
    @Upsert suspend fun ledger(ledger: ReminderLedger)
}

@Database(entities = [SessionRecord::class, Checkpoint::class, ReminderLedger::class], version = 2, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() { abstract fun dao(): LocalDao }

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE checkpoint ADD COLUMN localDate TEXT NOT NULL DEFAULT ''")
    }
}

class Preferences(private val context: Context) {
    private val key = stringPreferencesKey("settings_v1")
    suspend fun get(): Settings {
        val raw = context.preferenceStore.data.first()[key] ?: return Settings()
        val j = JSONObject(raw)
        val defaults = Settings()
        val a = j.optJSONArray("days") ?: JSONArray(listOf(1,2,3,4,5))
        return Settings(
            safetyAccepted = j.optBoolean("safety"), reminders = j.optBoolean("reminders"),
            days = (0 until a.length()).map { a.getInt(it) }.toSet(),
            startMinute = j.optInt("start", defaults.startMinute), endMinute = j.optInt("end", defaults.endMinute),
            intervalMinutes = j.optInt("interval", 60), quietEnabled = j.optBoolean("quiet", true),
            quietStart = j.optInt("quietStart", 720), quietEnd = j.optInt("quietEnd", 780),
            theme = j.optString("theme", "system"), reducedMotion = j.optBoolean("reduced"),
            haptic = j.optBoolean("haptic")
        ).let { if (it.valid()) it else defaults.copy(safetyAccepted = it.safetyAccepted, reminders = false) }
    }
    suspend fun set(s: Settings) {
        require(s.valid())
        val j = JSONObject().put("safety", s.safetyAccepted).put("reminders", s.reminders)
            .put("days", JSONArray(s.days.sorted())).put("start", s.startMinute).put("end", s.endMinute)
            .put("interval", s.intervalMinutes).put("quiet", s.quietEnabled).put("quietStart", s.quietStart)
            .put("quietEnd", s.quietEnd).put("theme", s.theme).put("reduced", s.reducedMotion).put("haptic", s.haptic)
        context.preferenceStore.edit { it[key] = j.toString() }
    }
}

fun EngineSnapshot.toJson(): String = JSONObject().put("routineId", routineId).put("id", id)
    .put("segment", segment).put("segmentElapsedMs", segmentElapsedMs).put("activeMs", activeMs)
    .put("movementMs", movementMs).put("skippedMoves", skippedMoves).put("paused", paused)
    .put("finished", finished).put("status", status).toString()

fun decodeCheckpoint(raw: String): EngineSnapshot {
    val j = JSONObject(raw)
    return EngineSnapshot(j.getString("routineId"), j.getString("id"), j.getInt("segment"),
        j.getLong("segmentElapsedMs"), j.getLong("activeMs"), j.getLong("movementMs"),
        j.getInt("skippedMoves"), true, j.getBoolean("finished"), j.getString("status"))
}
