package com.example.productdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutbuddy.WeeklySchedule
import com.example.workoutbuddy.WorkoutDAO

@Database(entities = [WeeklySchedule::class], version = 1)
abstract class WeeklyScheduleDB : RoomDatabase() {
    abstract fun WorkoutDAO() : WorkoutDAO

    companion object {
        private var INSTANT: WeeklyScheduleDB? = null

        fun getDBObject(context: Context): WeeklyScheduleDB? {
            if (INSTANT == null) {
                synchronized(WeeklyScheduleDB::class.java) {
                    INSTANT = Room.databaseBuilder(context, WeeklyScheduleDB::class.java, "weeklyscheduleDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}