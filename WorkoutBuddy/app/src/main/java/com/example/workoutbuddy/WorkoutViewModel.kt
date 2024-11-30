package com.example.workoutbuddy

import androidx.lifecycle.ViewModel

class WorkoutViewModel {

    var userInfo = MuteableLiveData<User>()
    var workoutfocuses = MuteableLiveData<ArrayList<String>>()
    var weeklyschedule = MuteableLiveData<WeeklyScheduleDB>()
    var exercises = MuteableLiveData<ExerciseDB>


    init {
        workoutfocuses.value = ArrayList()
    }

    fun addfocus(focus: String) {
        workoutfocuses.value?.add(focus)
    }

    fun removeFocus() {
        if (workoutfocuses.value?.size !!>0) {
            workoutfocuses.value?.remove(workoutfocuses.value?.last())
        }
    }

}