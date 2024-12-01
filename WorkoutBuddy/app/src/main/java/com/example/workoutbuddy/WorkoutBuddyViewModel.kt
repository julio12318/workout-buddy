package com.example.workoutbuddy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutBuddyViewModel : ViewModel() {
    val apiManager = MutableLiveData<APIManager>()
    val requestedWorkouts = MutableLiveData<ArrayList<APIWorkoutObject>>()

    init {
        apiManager.value = APIManager(this)
        requestedWorkouts.value = ArrayList<APIWorkoutObject>()
    }

    fun startExercises() {
//        apiManager.value?.fetchExercisesByMuscle("serratus anterior");
    }
}