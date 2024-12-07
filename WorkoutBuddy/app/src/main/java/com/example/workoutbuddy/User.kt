package com.example.workoutbuddy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
class User() {

    @PrimaryKey
    var name = ""
    var points = 0
    var question1 = ""
    var question2 = ""
}