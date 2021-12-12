package pl.cutter72.sem5.notatnik.database.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * https://developer.android.com/training/data-storage/room/
 */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val content: String
)
