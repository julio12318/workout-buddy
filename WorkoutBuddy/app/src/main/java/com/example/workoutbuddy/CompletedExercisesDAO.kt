package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompletedExercisesDAO {
    @Query("SELECT * FROM completedexercisestable")
    fun getAll():List<CompletedExercises>

}