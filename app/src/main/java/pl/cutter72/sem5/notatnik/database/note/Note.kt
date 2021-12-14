package pl.cutter72.sem5.notatnik.database.note

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * https://developer.android.com/training/data-storage/room/
 *
 * @author Paweł Drelich, WSB Gdańsk, indeks: 54589, grupa: ININ5_PR1.1
 */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var content: String = ""
) {

    fun isBlank(): Boolean {
        return title.isBlank() && content.isBlank()
    }

    fun isPreparedToSave(): Boolean {
        if (isBlank()) {
            return false
        }
        prepareToSave()
        return true
    }

    fun clear() {
        title = ""
        content = ""
    }

    private fun prepareToSave() {
        if (title.isBlank()) {
            title = "Empty title"
        }
        if (content.isBlank()) {
            content = "Empty content"
        }
    }
}
