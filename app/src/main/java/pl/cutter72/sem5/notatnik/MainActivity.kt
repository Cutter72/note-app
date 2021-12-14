package pl.cutter72.sem5.notatnik

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
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

    private val DELETE_ACTION_INDEX: Int = 4
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = AppDatabase.getDatabase(this)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu!!
        startEditFragment()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_save -> {
                saveNote(note.copy())
                true
            }
            R.id.action_add -> {
                createNewNote()
                true
            }
            R.id.action_delete -> {
                deleteSelectedNotes()
                true
            }
            R.id.action_list_notes -> {
                listNotes()
                true
            }
            R.id.action_edit -> {
                editNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteSelectedNotes() {
        GlobalScope.launch {
            val notesToDelete: MutableList<Note> = mutableListOf()
            for ((key, value) in notesMap) {
                if ((key as CheckBox).isChecked) {
                    notesToDelete.add(value)
                }
            }
            if (notesToDelete.isEmpty()) {
                runOnUiThread {
                    showToast(R.string.nothing_to_delete)
                }
                return@launch
            }
            dbDeleteNotes(notesToDelete)
            listNotes()
        }
    }

    private fun createNewNote() {
        if (!note.isBlank()) {
            saveNote(note.copy())
        }
        note = Note()
        GlobalScope.launch {
            dbCreate(note)
            runOnUiThread {
                editNotes()
            }
        }
    }

    private fun saveNote(notePendingToSave: Note) {
        GlobalScope.launch {
            if (notePendingToSave.isPreparedToSave()) {
                if (notePendingToSave.id == 0L) {
                    dbCreate(notePendingToSave)
                    runOnUiThread {
                        showToast(R.string.saved)
                    }
                } else {
                    dbUpdate(notePendingToSave)
                }
                runOnUiThread {
                    refreshEditFragment()
                }
            }
        }
    }

    private fun dbDelete(notePendingToDelete: Note) {
        db.noteDao().delete(notePendingToDelete)
        runOnUiThread {
            showToast(R.string.deleted)
        }
    }

    private fun dbDeleteNotes(notesPendingToDelete: List<Note>) {
        db.noteDao().deleteNotes(notesPendingToDelete)
        runOnUiThread {
            showToast(R.string.deleted)
        }
    }

    private fun dbCreate(notePendingToSave: Note) {
        note.id = db.noteDao().create(notePendingToSave)
    }

    private fun dbUpdate(notePendingToSave: Note) {
        db.noteDao().update(notePendingToSave)
        runOnUiThread {
            showToast(R.string.updated)
        }
    }

    private fun editNotes() {
        startEditFragment()
    }

    private fun listNotes() {
        GlobalScope.launch {
            val list = db.noteDao().readAll()
            NoteListFragment.list = list
            runOnUiThread {
                startListFragment()
            }
        }
    }

    fun startEditFragment() {
        menu.getItem(DELETE_ACTION_INDEX).isEnabled = false
//        val menu = findViewById<>(R.menu.menu_main)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(
            R.id.fragment_container,
            NoteEditFragment(),
            NoteEditFragment::class.simpleName
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startListFragment() {
        menu.getItem(DELETE_ACTION_INDEX).isEnabled = true
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(
            R.id.fragment_container,
            NoteListFragment(),
            NoteListFragment::class.simpleName
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun refreshEditFragment() {
        supportFragmentManager.findFragmentByTag(NoteEditFragment::class.simpleName)?.onResume()
    }

    private fun showToast(@StringRes stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
    }

    companion object {
        var note = Note()
        val notesMap: MutableMap<View, Note> = mutableMapOf()
    }
}