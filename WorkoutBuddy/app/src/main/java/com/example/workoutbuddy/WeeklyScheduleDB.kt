package com.example.workoutbuddy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutbuddy.WeeklySchedule

@Database(entities = [WeeklySchedule::class], version = 1)
abstract class WeeklyScheduleDB : RoomDatabase() {
    abstract fun WeeklyScheduleDAO() : WeeklyScheduleDAO

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