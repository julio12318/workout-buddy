package com.example.workoutbuddy


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "WeeklySchedule")
class WeeklySchedule {

    @PrimaryKey
    var date = java.util.Date()
    var dow = ""
    var focus = ""
    var exercises = ArrayList<Exercise>()


}