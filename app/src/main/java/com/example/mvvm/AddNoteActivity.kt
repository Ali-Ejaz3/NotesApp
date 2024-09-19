package com.example.mvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm.Models.Notes
import com.example.mvvm.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {
    private lateinit var note: Notes
    private lateinit var old_note:Notes
    var isUpdate = false
    private lateinit var binding: ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_note = intent.getSerializableExtra("current_note") as Notes
            binding.etTitle.setText(old_note.title)
            binding.etNote.setText(old_note.note)
            isUpdate = true
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE,d MMM yyyy HH:mm a")
                if(isUpdate){
                    note = Notes(
                        old_note.id,title,note_desc,formatter.format(Date())
                    )
                }else{
                    note = Notes(
                        null,title,note_desc,formatter.format(Date())
                    )

                }
                    val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this, "Please Enter Some Data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }


    }
}