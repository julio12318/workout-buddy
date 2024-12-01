package com.example.workoutbuddy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Exercise::class], version = 1)
abstract class ExerciseDB : RoomDatabase() {
    abstract fun ExerciseDAO() : ExerciseDAO

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