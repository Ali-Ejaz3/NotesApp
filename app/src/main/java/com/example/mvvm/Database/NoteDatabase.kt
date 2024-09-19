package com.example.mvvm.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm.Models.Notes
import com.example.mvvm.utilities.DATABASE_NAME

@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getDao():NotesDao

    companion object{
        private var INSTANCE:NoteDatabase? = null

        fun getDatabase(context:Context):NoteDatabase {
            return INSTANCE?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}