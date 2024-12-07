package com.example.workoutbuddy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChildPastAdapter(
    var exercisesList:ArrayList<CompletedExercises>,
    var click: (CompletedExercises) -> Unit
): RecyclerView.Adapter<ChildPastAdapter.ChildPastViewHolder>(){

    class ChildPastViewHolder(val viewItem: View): RecyclerView.ViewHolder(viewItem){
        fun bind(exercise:CompletedExercises, click: (CompletedExercises) -> Unit){
            viewItem.findViewById<TextView>(R.id.name_text).text=exercise.name
            viewItem.findViewById<TextView>(R.id.part_text).text=exercise.bodyPart
            val gifView = viewItem.findViewById<ImageView>(R.id.image)
            Glide.with(viewItem.context).load(exercise.gifUrl).into(gifView)
            viewItem.setOnClickListener {
                click(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildPastViewHolder {
        val viewItem= LayoutInflater.from(parent.context).inflate(R.layout.exercises_view, parent, false)
        return ChildPastViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    override fun onBindViewHolder(holder: ChildPastViewHolder, position: Int) {
        holder.bind(exercisesList[position], click)
    }
}