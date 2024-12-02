package com.example.workoutbuddy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutBuddyViewModel : ViewModel() {
    val apiManager = MutableLiveData<APIManager>()

    val database1 = MutableLiveData<ExerciseDB>()
    val database2 = MutableLiveData<CompletedExercisesDB>()
    val database3 = MutableLiveData<WeeklyScheduleDB>()
    val database4 = MutableLiveData<UserDB>()

    val requestedWorkouts = MutableLiveData<ArrayList<APIWorkoutObject>>()
    val user = MutableLiveData<User>()




    init {
        apiManager.value = APIManager(this)
        requestedWorkouts.value = ArrayList<APIWorkoutObject>()
    }

    fun startExercises() {
//        apiManager.value?.fetchExercisesByMuscle("serratus anterior");
    }

    fun startOrGetUser() {
        val userList = database4.value?.userDAO()?.getAll()
        if (userList != null) {
            if (userList.isEmpty()) {
                Log.d("List is Empty?", "List is for sure empty")
                val newUser = User()
                database4.value?.userDAO()?.insert(newUser)
                user.value = newUser
            }
            else {
                Log.d("List is Empty?", "List is for sure not empty")
                val currentUser = userList[0]
                user.value = currentUser
            }
        }
    }

    fun changeName(oldName: String, newName: String) {
        database4.value?.userDAO()?.updateName(oldName, newName)
    }

    fun changeQuestions(answer1: String, answer2: String, name: String) {
        database4.value?.userDAO()?.updateQuestions(answer1, answer2, name)
    }
}