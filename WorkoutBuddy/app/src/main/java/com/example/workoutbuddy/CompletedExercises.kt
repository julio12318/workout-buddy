package com.example.workoutbuddy

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "CompletedExercisesTable")
class CompletedExercises {

    @PrimaryKey
    var id = UUID.randomUUID()

    var exerciseID = ""
    var datecompleted = ""
    var musclegroup = ""
    var weight = 0
    var sets = 0
    var reps = 0

}