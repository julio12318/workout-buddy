package com.example.workoutbuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WeekAdapter(
    var weekList:ArrayList<Exercise>,
    var click: (Exercise) -> Unit
): RecyclerView.Adapter<WeekAdapter.WeekViewHolder>(){

    class WeekViewHolder(val viewItem: View): RecyclerView.ViewHolder(viewItem){
        fun bind(exercise:Exercise, click:(Exercise)->Unit){
            val nameCap = "${exercise.name.split(" ").joinToString(" ") { it.capitalize() }}"
            viewItem.findViewById<TextView>(R.id.name_text).text=nameCap
            val bodyPartCap = "${exercise.bodyPart.split(" ").joinToString(" ") { it.capitalize() }}"
            viewItem.findViewById<TextView>(R.id.part_text).text=bodyPartCap
            val gifView = viewItem.findViewById<ImageView>(R.id.image)
            Glide.with(viewItem.context).load(exercise.gifUrl).into(gifView)
            viewItem.setOnClickListener {
                click(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val viewItem= LayoutInflater.from(parent.context).inflate(R.layout.exercises_view, parent, false)
        return WeekViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return weekList.size
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        holder.bind(weekList[position], click)
    }
}