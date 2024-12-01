package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseDAO {
    @Query("SELECT * FROM exercisetable")
    fun getAll():List<Exercise>

}