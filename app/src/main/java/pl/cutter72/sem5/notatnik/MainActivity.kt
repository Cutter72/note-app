package pl.cutter72.sem5.notatnik

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.cutter72.sem5.notatnik.database.AppDatabase
import pl.cutter72.sem5.notatnik.database.note.Note
import pl.cutter72.sem5.notatnik.databinding.ActivityMainBinding

/**
 * https://developer.android.com/guide/topics/ui/menus
 */
class MainActivity : AppCompatActivity() {

    private var currentNote: Note = Note()
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = AppDatabase.getDatabase(this)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_save -> {
                saveNote()
                true
            }
            R.id.action_create_new -> {
                createNewNote()
                true
            }
            R.id.action_delete -> {
                deleteNote()
                true
            }
            R.id.action_test -> {
                test()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote() {
        GlobalScope.launch {
            db.noteDao().delete(currentNote)
            runOnUiThread {
                showToast(R.string.note_deleted)
            }
            currentNote = Note()
        }


    }

    private fun createNewNote() {
        saveNote()
        currentNote = Note()
        updateNoteUi()
    }

    private fun saveNote() {
        updateNoteEntity()
        GlobalScope.launch {
            db.noteDao().update(currentNote)
            runOnUiThread {
                showToast(R.string.note_saved)
            }
        }
    }

    private fun test() {
        //todo lists all notes
    }

    private fun updateNoteEntity() {
        currentNote.title = binding.textTitle.editText?.text.toString()
        currentNote.content = binding.textContent.editText?.text.toString()
    }

    private fun showToast(@StringRes stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
    }

    private fun updateNoteUi() {
        binding.textTitle.editText?.setText(currentNote.title)
        binding.textContent.editText?.setText(currentNote.content)
    }
}