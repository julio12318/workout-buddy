package com.example.workoutbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ParentPastAdapter(
    var itemsList:ArrayList<ParentItem>,
    var isDate: Boolean,
    var click: (CompletedExercises) -> Unit
): RecyclerView.Adapter<ParentPastAdapter.ParentPastViewHolder>(){



    class ParentPastViewHolder(val viewItem: View): RecyclerView.ViewHolder(viewItem){
        fun bind(parent:ParentItem, isDate: Boolean, click: (CompletedExercises) -> Unit){
            if (isDate) {
                val floatTitle = parent.title.toLong()
                val date = Date(floatTitle)
                val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(date)
                viewItem.findViewById<TextView>(R.id.title).text=formattedDate
            }
            else {
                viewItem.findViewById<TextView>(R.id.title).text=parent.title
            }


            val childRecyclerView = viewItem.findViewById<RecyclerView>(R.id.itemrecyclerview)
            val recyclerViewManager = GridLayoutManager(viewItem.context,2)
            val recyclerViewAdapter = ChildPastAdapter(parent.comExerciseList, click)

            childRecyclerView.apply {
                layoutManager = recyclerViewManager
                adapter = recyclerViewAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentPastViewHolder {
        val viewItem= LayoutInflater.from(parent.context).inflate(R.layout.type_view, parent, false)
        return ParentPastViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ParentPastViewHolder, position: Int) {
        holder.bind(itemsList[position], isDate, click)
    }
}