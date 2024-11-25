package com.example.workoutbuddy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutBuddyViewModel : ViewModel() {
    val apiManager = MutableLiveData<APIManager>()

    init {
        apiManager.value = APIManager(this)
    }
}