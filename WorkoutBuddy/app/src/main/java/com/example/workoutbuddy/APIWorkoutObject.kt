package com.example.workoutbuddy

class APIWorkoutObject (
    val bodyPart : String,
    val equipment: String,
    val gifUrl: String,
    val workoutID: String,
    val name: String,
    val target: String,
    val secondaryMuscles : Array<String>,
    val instructions : Array<String>
) {
}