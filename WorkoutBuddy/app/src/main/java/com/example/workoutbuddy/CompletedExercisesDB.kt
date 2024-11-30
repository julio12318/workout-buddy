package com.example.productdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutbuddy.CompletedExercises
import com.example.workoutbuddy.WeeklySchedule
import com.example.workoutbuddy.WorkoutDAO

@Database(entities = [CompletedExercises::class], version = 1)
abstract class CompletedExercisesDB : RoomDatabase() {
    abstract fun WorkoutDAO() : WorkoutDAO

    companion object {
        private var INSTANT: CompletedExercisesDB? = null

        fun getDBObject(context: Context): CompletedExercisesDB? {
            if (INSTANT == null) {
                synchronized(CompletedExercisesDB::class.java) {
                    INSTANT = Room.databaseBuilder(context, CompletedExercisesDB::class.java, "completedexerciseDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}