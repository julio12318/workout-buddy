package com.example.workoutbuddy

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "ExerciseTable")
class Exercise() {

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
}