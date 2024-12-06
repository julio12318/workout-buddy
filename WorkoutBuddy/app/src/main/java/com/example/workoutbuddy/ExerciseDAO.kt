package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseDAO {
    @Query("SELECT * FROM ExerciseTable")
    fun getAll():List<Exercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exercise: Exercise)

    @Query("SELECT * FROM ExerciseTable WHERE dateCreated=:date")
    fun getDateEx(date: Long):List<Exercise>
}