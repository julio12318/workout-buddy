package com.example.workoutbuddy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExerciseTable")
class Exercise() {

    @PrimaryKey
    var id = ""

    var bodyPart = ""
    var equipment = ""
    var gifUrl = ""
    var name = ""
    var target = ""
    var secondaryMuscles = ""
    var instructions = ""
}