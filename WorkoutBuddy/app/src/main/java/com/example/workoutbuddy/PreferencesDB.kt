package com.example.workoutbuddy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutbuddy.WeeklySchedule

@Database(entities = [Preferences::class], version = 1)
abstract class PreferencesDB : RoomDatabase() {
    abstract fun preferencesDAO() : PreferencesDAO

    companion object {
        private var INSTANT: PreferencesDB? = null

        fun getDBObject(context: Context): PreferencesDB? {
            if (INSTANT == null) {
                synchronized(PreferencesDB::class.java) {
                    INSTANT = Room.databaseBuilder(context, PreferencesDB::class.java, "preferencesDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}