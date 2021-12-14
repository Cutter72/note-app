package pl.cutter72.sem5.notatnik.database.note

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

/**
 * https://developer.android.com/training/data-storage/room/
 *
 * @author Paweł Drelich, WSB Gdańsk, indeks: 54589, grupa: ININ5_PR1.1
 */
@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    fun create(note: Note): Long

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Delete
    fun deleteNotes(note: List<Note>)

    @Query("SELECT * FROM note")
    fun readAll(): List<Note>
}