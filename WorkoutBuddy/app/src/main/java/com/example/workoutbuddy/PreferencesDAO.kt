package com.example.workoutbuddy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PreferencesDAO {
    @Query("SELECT * FROM PreferencesTable")
    fun getAll():List<Preferences>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(preferences: Preferences)

    @Query("SELECT * FROM PreferencesTable WHERE name=:part")
    fun getPreferences(part: String): List<Preferences>

    @Query("UPDATE PreferencesTable SET isChecked=:checked WHERE name=:name")
    fun updatePreferences(name: String, checked: Boolean)
}