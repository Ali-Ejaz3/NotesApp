package com.example.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mvvm.Database.NoteDatabase
import com.example.mvvm.Database.NotesRepository
import com.example.mvvm.Models.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository
    val allnotes: LiveData<List<Notes>>

    init {
        val dao = NoteDatabase.getDatabase(application).getDao()
        repository = NotesRepository(dao)
        allnotes = repository.allNotes
    }

    fun deleteNote(note: Notes) = viewModelScope.launch (Dispatchers.IO){
        repository.delete(note)
    }

    fun insert(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }



    fun update(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
}
