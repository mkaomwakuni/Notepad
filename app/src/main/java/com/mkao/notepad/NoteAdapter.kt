import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mkao.notepad.MainActivity
import com.mkao.notepad.Note
import com.mkao.notepad.R

class NoteAdapter(private val mainActivity:
                  MainActivity
): RecyclerView.Adapter<NoteAdapter.ViewHolderNote>() {
    var noteList = mutableListOf<Note>()

    inner class ViewHolderNote(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        internal var NTitle = view.findViewById<View>(R.id.viewTitle) as TextView
        internal var NContents = view.findViewById<View>(R.id.viewContents) as TextView
        init {
            view.isClickable = true
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mainActivity.showNote(layoutPosition) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolderNote {
        return ViewHolderNote( LayoutInflater.from(parent.context).inflate(R.layout.note_previews, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderNote, position: Int) {
        val note = noteList[position]
        holder.NTitle.text = note.title
        holder.NContents.text = if
                (note.contents.length < 25)
                    note.contents else note.contents.substring(0, 25) + "..."
    }
    override fun getItemCount(): Int = noteList.size
}