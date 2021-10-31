package com.example.notesappviewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    private var notes:LiveData<List<NoteDetail>>
    val noteDao=NotesDatabase.getInstance(application).NoteDao()


    init {
            notes=noteDao.getNotes()
    }

    fun getnotes():LiveData<List<NoteDetail>>{
        return notes
    }

    fun addNote(s:String){
        if (s.isNotEmpty()){
            val note=NoteDetail(0,s)
            CoroutineScope(IO).launch {
                noteDao.insertNote(note)
            }
        }else{
            Toast.makeText(getApplication(),"Enter Note",Toast.LENGTH_LONG).show()
        }
    }

    fun deleteNote(noteDetail: NoteDetail){
        CoroutineScope(IO).launch {
            noteDao.deleteNote(noteDetail)
        }
    }

    fun updateNote(id:Int,s:String){
        if(s.isNotEmpty()){
            CoroutineScope(IO).launch {
                noteDao.updateNote(id,s)
            }
        }
    }


}