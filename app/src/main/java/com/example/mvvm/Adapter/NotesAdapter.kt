package com.example.mvvm.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.Models.Notes
import com.example.mvvm.R
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NoteClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NoteList= ArrayList<Notes>()
    private val FullList= ArrayList<Notes>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return NoteList.size
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NoteList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.Note_tv.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )

        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(NoteList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(NoteList[holder.adapterPosition], holder.notes_layout)
            true
        }
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_layout = itemView.findViewById<CardView>(R.id.cardlayout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val Note_tv = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)

    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    fun updateList(newList: List<Notes>) {
        FullList.clear()
        FullList.addAll(newList)

        NoteList.clear()
        NoteList.addAll(FullList)
        notifyDataSetChanged()
    }

    interface NoteClickListener {

        fun onItemClicked(notes: Notes)
        fun onLongItemClicked(notes: Notes, cardView: CardView)
    }

    fun filterList(search: String) {
        NoteList.clear()

        for (item in FullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true
            ){
                NoteList.add(item)
            }
        }
        notifyDataSetChanged()
    }

}