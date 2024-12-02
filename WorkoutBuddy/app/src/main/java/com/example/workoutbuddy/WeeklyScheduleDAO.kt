package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeeklyScheduleDAO {
    @Query("SELECT * FROM WeeklyScheduleTable")
    fun getAll():List<WeeklySchedule>

}