package pl.cutter72.sem5.notatnik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import pl.cutter72.sem5.notatnik.database.note.Note
import pl.cutter72.sem5.notatnik.databinding.FragmentNoteListBinding

class NoteListFragment() : Fragment() {
    private lateinit var binding: FragmentNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    fun updateUi() {
        binding.noteList.removeAllViews()
        MainActivity.notesMap.clear()
        for (note in list) {
            val checkBox = CheckBox(context)
            prepareCheckBox(note, checkBox)
            binding.noteList.addView(checkBox)
            MainActivity.notesMap.put(checkBox, note)
        }
        Toast.makeText(context, R.string.long_click_to_edit_note, Toast.LENGTH_LONG).show()
    }

    private fun prepareCheckBox(
        note: Note,
        checkBox: CheckBox
    ) {
        val fullText = "${note.title}:\n${note.content}"
        checkBox.setText(fullText)
        checkBox.layoutParams = binding.noteList.layoutParams
        setLockClickListener(checkBox)
        setOnclickListener(checkBox.currentTextColor, checkBox)
    }

    private fun setLockClickListener(checkBox: CheckBox) {
        checkBox.isLongClickable = true
        val longClickListener: View.OnLongClickListener = object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                MainActivity.note = MainActivity.notesMap[v]!!
                (activity as MainActivity).startEditFragment()
                return true
            }

        }
        checkBox.setOnLongClickListener(longClickListener)
    }

    private fun setOnclickListener(defaultTextColor: Int, checkBox: CheckBox) {
        val onClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                if ((v as CheckBox).isChecked) {
                    v.setTextColor(resources.getColor(R.color.purple_200))
                } else {
                    v.setTextColor(defaultTextColor)
                }
            }
        }
        checkBox.setOnClickListener(onClickListener)
    }

    companion object {
        var list: List<Note> = ArrayList()
    }
}