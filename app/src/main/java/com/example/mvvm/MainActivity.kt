package com.example.mvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvm.Adapter.NotesAdapter
import com.example.mvvm.Database.NoteDatabase
import com.example.mvvm.Models.Notes
import com.example.mvvm.ViewModel.NoteViewModel
import com.example.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),NotesAdapter.NoteClickListener,PopupMenu.OnMenuItemClickListener {
    private lateinit var binding:ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote:Notes

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode==Activity.RESULT_OK){
            val note =result.data?.getSerializableExtra("note") as? Notes
            if (note != null){
                viewModel.update(note)

            }        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)
    }



    private fun initUi() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = NotesAdapter(this,this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode==Activity.RESULT_OK){

                val note = result.data?.getSerializableExtra("note") as Notes
                if(note!= null){
                    viewModel.insert(note)

                }
            }

        }
        binding.fab.setOnClickListener {
            val intent = Intent(this,AddNoteActivity::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    adapter.filterList(newText)
                }
                return true
            }

        })
    }

    override fun onItemClicked(notes: Notes) {
        val intent = Intent(this,AddNoteActivity::class.java)
        intent.putExtra("current_note",notes)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(notes: Notes, cardView: CardView) {
        selectedNote = notes
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.menu_item)
        popup.show()
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.delete){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}