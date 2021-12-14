package pl.cutter72.sem5.notatnik

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.cutter72.sem5.notatnik.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment() {


    private lateinit var binding: FragmentNoteEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteEditBinding.inflate(layoutInflater)
        initializeTextWatchers()
        return binding.root
    }

    private fun initializeTextWatchers() {
        val titleTextWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // intentionally empty
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // intentionally empty
            }

            override fun afterTextChanged(p0: Editable?) {
                MainActivity.note.title = p0.toString()
            }

        }
        val contentTextWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // intentionally empty
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // intentionally empty
            }

            override fun afterTextChanged(p0: Editable?) {
                MainActivity.note.content = p0.toString()
            }

        }
        binding.titleEditText.addTextChangedListener(titleTextWatcher)
        binding.contentEditText.addTextChangedListener(contentTextWatcher)
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi() {
        binding.idText.text = "ID:${MainActivity.note.id}"
        binding.titleEditText.setText(MainActivity.note.title)
        binding.contentEditText.setText(MainActivity.note.content)
    }
}