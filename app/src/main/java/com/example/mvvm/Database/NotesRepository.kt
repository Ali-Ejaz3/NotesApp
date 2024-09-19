package com.example.mvvm.Database

import androidx.lifecycle.LiveData
import com.example.mvvm.Models.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = notesDao.getAllData()

    suspend fun insert(note:Notes){
        notesDao.insertData(note)
    }
    suspend fun delete(note: Notes){
        notesDao.deleteData(note)
    }

    suspend fun update(note: Notes){
        notesDao.updateData(note.id,note.title!!, note.note!!)
    }
}