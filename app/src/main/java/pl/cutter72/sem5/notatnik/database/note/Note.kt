package pl.cutter72.sem5.notatnik.database.note

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * https://developer.android.com/training/data-storage/room/
 */
@Entity
data class Note(
    var title: String = "",
    var content: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
