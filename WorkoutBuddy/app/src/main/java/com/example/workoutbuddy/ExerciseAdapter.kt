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

class ExerciseAdapter(
    var exercisesList:ArrayList<APIWorkoutObject>,
    var click: (APIWorkoutObject) -> Unit
): RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>(){

    class ExerciseViewHolder(val viewItem: View): RecyclerView.ViewHolder(viewItem){
        fun bind(exercise:APIWorkoutObject, click:(APIWorkoutObject)->Unit){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val viewItem= LayoutInflater.from(parent.context).inflate(R.layout.exercises_view, parent, false)
        return ExerciseViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercisesList[position], click)
    }
}