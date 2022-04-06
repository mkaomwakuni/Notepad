package com.mkao.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.mkao.notepad.databinding.ShowNoteBinding

class ShowNote(private val note: Note,private val index:Int):DialogFragment() {

    private var _binding: ShowNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val callingclass = activity as MainActivity
        val inflater = callingclass.layoutInflater
        _binding = ShowNoteBinding.inflate(inflater)
        val builder = AlertDialog.Builder(callingclass)
            .setView(binding.root)
        binding.shwTitle.text = note.title
        binding.shwContents.text = note.contents
        binding.shwokbtn.setOnClickListener {
            dismiss()
        }
        binding.shwDismiss.setOnClickListener {
            callingclass.deleteNote(index)
            Toast.makeText(
                callingclass,
                resources.getString(R.string.Note_Deleted),
                Toast.LENGTH_SHORT
            )
                .show()
            dismiss()

        }
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}