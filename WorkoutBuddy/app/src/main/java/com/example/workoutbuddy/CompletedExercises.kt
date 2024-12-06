package com.example.workoutbuddy

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "CompletedExercisesTable")
class CompletedExercises {

    @PrimaryKey
    var id = UUID.randomUUID()

    var bodyPart = ""
    var equipment = ""
    var gifUrl = ""
    var name = ""
    var target = ""
    var secondaryMuscles = ""
    var instructions = ""
    var dateCreated: Long = 0
    var minutes = 0
    var recommend = true
    var notes = ""

}