package com.example.workoutbuddy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.util.Log


@Database(entities = [Exercise::class], version = 1)
abstract class ExerciseDB : RoomDatabase() {
    abstract fun exerciseDAO() : ExerciseDAO

    companion object {
        private var INSTANT: ExerciseDB? = null

        fun getDBObject(context: Context): ExerciseDB? {
            Log.d("Step 2", "Made it to Step 2")
            if (INSTANT == null) {
                Log.d("Step 3", "Made it to Step 3")
                synchronized(ExerciseDB::class.java) {
                    Log.d("Step 4", "Made it to Step 4")
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