package pl.cutter72.sem5.notatnik.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.cutter72.sem5.notatnik.database.note.Note

/**
 * https://developer.android.com/training/data-storage/room/
 */
@Database(entities = [Note::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): Note
}