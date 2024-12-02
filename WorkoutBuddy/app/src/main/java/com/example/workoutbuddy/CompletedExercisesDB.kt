package com.example.workoutbuddy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CompletedExercises::class], version = 1)
abstract class CompletedExercisesDB : RoomDatabase() {
    abstract fun completedExercisesDAO() : CompletedExercisesDAO

    companion object {
        private var INSTANT: CompletedExercisesDB? = null

        fun getDBObject(context: Context): CompletedExercisesDB? {
            if (INSTANT == null) {
                synchronized(CompletedExercisesDB::class.java) {
                    INSTANT = Room.databaseBuilder(context, CompletedExercisesDB::class.java, "completedexercisesDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}