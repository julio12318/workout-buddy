package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompletedExercisesDAO {
    @Query("SELECT * FROM CompletedExercisesTable")
    fun getAll():List<CompletedExercises>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(completedExercise: CompletedExercises)

    @Query("SELECT DISTINCT dateCreated FROM CompletedExercisesTable ORDER BY dateCreated DESC")
    fun getCompDate():List<Long>

    @Query("SELECT * FROM CompletedExercisesTable WHERE dateCreated=:date")
    fun getDate(date: Long):List<CompletedExercises>

    @Query("SELECT DISTINCT bodyPart FROM CompletedExercisesTable ORDER BY bodyPart")
    fun getCompPart():List<String>

    @Query("SELECT * FROM CompletedExercisesTable WHERE bodyPart=:part")
    fun getPart(part: String):List<CompletedExercises>

    @Query("SELECT DISTINCT rating FROM CompletedExercisesTable ORDER BY ratingNum")
    fun getCompRating():List<String>

    @Query("SELECT * FROM CompletedExercisesTable WHERE rating=:rating")
    fun getRating(rating: String):List<CompletedExercises>
}