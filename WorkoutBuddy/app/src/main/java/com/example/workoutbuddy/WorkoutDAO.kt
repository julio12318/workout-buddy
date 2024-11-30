package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkoutDAO {

    @Query("SELECT * FROM ExerciseTable WHERE bodyPart = :partlist")
    fun getExcerciseByGroup (partlist: ArrayList<String>) : Exercise

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(p: Exercise)

    @Delete
    fun delete(p: Exercise)

}