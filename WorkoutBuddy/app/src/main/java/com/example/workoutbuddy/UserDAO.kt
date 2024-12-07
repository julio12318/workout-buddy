package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Query("SELECT * FROM UserTable")
    fun getAll():List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("UPDATE UserTable SET name=:newName WHERE name=:oldName")
    fun updateName(oldName: String, newName: String)

    @Query("UPDATE UserTable SET question1 = :answer1, question2 = :answer2 WHERE name = :name")
    fun updateQuestions(answer1: String, answer2: String, name: String)

    @Query("UPDATE UserTable SET points = :points")
    fun updatePoints(points: Int)
}