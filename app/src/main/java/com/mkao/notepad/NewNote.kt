package com.mkao.notepad
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.mkao.notepad.databinding.NewNoteBinding

class NewNote : DialogFragment(){
    private var _binding: NewNoteBinding?=null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val callingClass = activity as MainActivity
        val inflater = callingClass.layoutInflater
        _binding= NewNoteBinding.inflate(inflater)
        val builder = AlertDialog.Builder(callingClass)
            .setView(binding.root)
            .setMessage(resources.getString(R.string.Add_note))
        //Buttons here
        binding.btncancel.setOnClickListener{
            dismiss()
        }
        binding.btnOk.setOnClickListener {
            val title = binding.edTitle.text.toString()
            val contents = binding.edContents.text.toString()
            if (title.isNotEmpty()&& contents.isNotEmpty()){
                   val note = Note(title,contents)
                   callingClass.createNewNote(note)
                   Toast.makeText(callingClass,resources.getString(R.string.Note_Saved),Toast.LENGTH_SHORT)
                  .show()
                   dismiss()
            }else
                Toast.makeText(callingClass,resources.getString(R.string.note_empty),Toast.LENGTH_SHORT)
        }
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}