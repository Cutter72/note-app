package pl.cutter72.sem5.notatnik.database.note

import androidx.room.*

/**
 * https://developer.android.com/training/data-storage/room/
 */
@Dao
interface NoteDao {

    @Insert
    fun create(note: Note)

    @Query("SELECT * FROM note WHERE id = :id")
    fun read(id: Int): Note

    @Update
    fun update(note: Note)

    @Delete
    fun delete(user: Note)

    @Query("SELECT * FROM note")
    fun readAll(): List<Note>

    @Query("SELECT * FROM note WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Note
}