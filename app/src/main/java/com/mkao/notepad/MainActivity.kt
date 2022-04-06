package com.mkao.notepad

import NoteAdapter
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mkao.notepad.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.Writer

class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
        private lateinit var adapter: NoteAdapter
        //companion object converts user notes into json and save them internally in the app
        companion object{
            private const val FILEPATH ="notes.json"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteAdapter(this)
        binding.recylerView.layoutManager= LinearLayoutManager(applicationContext)
        binding.recylerView.itemAnimator= DefaultItemAnimator()
        binding.recylerView.adapter= adapter

        binding.fab.setOnClickListener { view ->
        NewNote().show(supportFragmentManager,"")
        }

        adapter.noteList=retriveNotes()
        adapter.notifyItemRangeInserted(0,adapter.noteList.size)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun createNewNote(note: Note) {
        adapter.noteList.add(note)
        adapter.notifyItemInserted(adapter.noteList.size -1)
        saveNotes()

    }

    private fun saveNotes() {
        val notes = adapter.noteList
        val gson =GsonBuilder().create()
        val jsonNote=gson.toJson(notes)
        var writer:Writer? =null
        try {
            val out = this.openFileOutput(FILEPATH,Context.MODE_PRIVATE)
            writer= OutputStreamWriter(out)
            writer.write(jsonNote)
        }
        catch (e:Exception){
            writer?.close()
        }
        finally {
            writer?.close()
        }
    }
    private fun retriveNotes():MutableList<Note>{
        var notelist = mutableListOf<Note>()
        if (this.getFileStreamPath(FILEPATH).isFile){
            var reader:BufferedReader?=null
            try {
                val fileinput = this.openFileInput(FILEPATH)
                reader = BufferedReader(InputStreamReader(fileinput))
                val stringBuilder = StringBuilder()
                for (line in reader.readLine()) stringBuilder.append(line)

                if (stringBuilder.isNotEmpty()){
                    val listType =object :TypeToken<List<Note>>(){}.type
                    notelist =Gson().fromJson(stringBuilder.toString(),listType)
                }
            }catch (e:java.lang.Exception){
                reader?.close()
            }finally {
                reader?.close()
            }
        }
        return notelist
    }

    fun deleteNote(index: Int) {
        adapter.noteList.removeAt(index)
        adapter.notifyItemRemoved(index)
        saveNotes()

    }

    fun showNote(layoutPosition: Int) {
        val dialog =ShowNote(adapter.noteList[layoutPosition], layoutPosition )

    }
}