package com.example.workoutbuddy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutBuddyViewModel : ViewModel() {
    val apiManager = MutableLiveData<APIManager>()
    val database1 = MutableLiveData<ExerciseDB>()
    val database2 = MutableLiveData<CompletedExercisesDB>()
    val database3 = MutableLiveData<WeeklyScheduleDB>()

    init {
        apiManager.value = APIManager(this)
    }

    fun startExercises() {
//        apiManager.value?.fetchExercisesByMuscle("serratus anterior");
    }
}