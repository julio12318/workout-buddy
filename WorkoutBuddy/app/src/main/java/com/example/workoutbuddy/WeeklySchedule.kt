package com.example.workoutbuddy

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "WeeklyScheduleTable")
class WeeklySchedule {

    @PrimaryKey
    var date = ""
    var dow = ""
    var focus = ""
    var exercises = ""


}