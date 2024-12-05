package com.example.workoutbuddy


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    var partList:ArrayList<Preferences>,
): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){

    class RecyclerViewHolder(val viewItem: View): RecyclerView.ViewHolder(viewItem){
        fun bind(pref: Preferences){
            viewItem.findViewById<TextView>(R.id.body_part).text=pref.name
            viewItem.findViewById<CheckBox>(R.id.checkBox).isChecked = pref.isChecked
            viewItem.findViewById<CheckBox>(R.id.checkBox).setOnClickListener {
                pref.isChecked = !pref.isChecked
                viewItem.findViewById<CheckBox>(R.id.checkBox).isChecked = pref.isChecked
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewItem= LayoutInflater.from(parent.context).inflate(R.layout.selection_view, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return partList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(partList[position])
    }
}