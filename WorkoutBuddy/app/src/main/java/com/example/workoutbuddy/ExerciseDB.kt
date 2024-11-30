package com.example.productdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutbuddy.Exercise
import com.example.workoutbuddy.WeeklySchedule
import com.example.workoutbuddy.WorkoutDAO

@Database(entities = [Exercise::class], version = 1)
abstract class ExerciseDB : RoomDatabase() {
    abstract fun WorkoutDAO() : WorkoutDAO

    companion object {
        private var INSTANT: ExerciseDB? = null

        fun getDBObject(context: Context): ExerciseDB? {
            if (INSTANT == null) {
                synchronized(ExerciseDB::class.java) {
                    INSTANT = Room.databaseBuilder(context, ExerciseDB::class.java, "exerciseDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}