package com.example.mvvm.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvm.Models.Notes

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(note: Notes)

    @Delete
    suspend fun deleteData(note: Notes)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllData():LiveData<List<Notes>>


    @Query("UPDATE notes_table SET title = :title, note = :note WHERE id = :id")
    suspend fun updateData(id: kotlin.Int?, title: String, note: String)
}