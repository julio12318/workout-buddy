package com.example.workoutbuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeekAdapter(
    var weekList:ArrayList<Exercise>,
    var click: (Exercise) -> Unit
): RecyclerView.Adapter<WeekAdapter.WeekViewHolder>(){

    class WeekViewHolder(val viewItem: View): RecyclerView.ViewHolder(viewItem){
        fun bind(exercise:Exercise, click:(Exercise)->Unit){
            viewItem.findViewById<TextView>(R.id.name_text).text=exercise.name
            viewItem.findViewById<TextView>(R.id.part_text).text=exercise.bodyPart
            viewItem.setOnClickListener {
                click(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val viewItem= LayoutInflater.from(parent.context).inflate(R.layout.week_view, parent, false)
        return WeekViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return weekList.size
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        holder.bind(weekList[position], click)
    }
}