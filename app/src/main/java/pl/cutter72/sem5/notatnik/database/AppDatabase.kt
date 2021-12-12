package pl.cutter72.sem5.notatnik.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.cutter72.sem5.notatnik.database.note.Note
import pl.cutter72.sem5.notatnik.database.note.NoteDao

/**
 * https://developer.android.com/training/data-storage/room/
 * https://developer.android.com/codelabs/android-room-with-a-view-kotlin#7
 */
@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notatnik-database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}