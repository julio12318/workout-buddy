package com.example.workoutbuddy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PreferencesTable")
class Preferences() {

    @PrimaryKey
    var name = ""
    var isChecked = false
}