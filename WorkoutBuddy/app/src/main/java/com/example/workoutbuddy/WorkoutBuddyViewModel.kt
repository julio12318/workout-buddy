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
    val database5 = MutableLiveData<PreferencesDB>()

    val requestedWorkouts = MutableLiveData<ArrayList<APIWorkoutObject>>()
    val user = MutableLiveData<User>()
    val bodyParts = MutableLiveData<ArrayList<String>>()
    val bodyPartList = MutableLiveData<ArrayList<Preferences>>()
    val exercisesNames = MutableLiveData<ArrayList<Exercise>>()




    init {
        apiManager.value = APIManager(this)
        requestedWorkouts.value = ArrayList<APIWorkoutObject>()
        bodyParts.value = ArrayList<String>()
        bodyPartList.value = ArrayList<Preferences>()
        exercisesNames.value = ArrayList<Exercise>()
    }

    fun startExercises(method: String, attribute: String) {
        if (method == "part") {
            apiManager.value?.fetchExercisesByBodyPart(attribute)
        }
//        apiManager.value?.fetchExercisesByMuscle("serratus anterior");
    }

    fun getParts() {
        apiManager.value?.fetchBodyParts()
        Log.d("Announcement", "Body Parts Fetched!")
        Log.d("Size", "Size of List: ${bodyPartList.value?.size}")

    }

    fun addExercise(exercise: Exercise) {
        database1.value?.exerciseDAO()?.insert(exercise)
        Log.d("Exercise Time", "${exercise.dateCreated}")
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

    fun getPreferences(part: String) {
        val prefList = database5.value?.preferencesDAO()?.getPreferences(part)
        if (prefList != null) {
            if (prefList.isEmpty()) {
                Log.d("Preferences Check", "${part} is not in list")
                val newPreferences = Preferences()
                newPreferences.name = part
                newPreferences.isChecked = false
                database5.value?.preferencesDAO()?.insert(newPreferences)
            }
            else {
                Log.d("Preferences Check", "${part} is in the list")
            }
        }
    }

    fun getAllPreferences() {
        val allPrefList = database5.value?.preferencesDAO()?.getAll()
        bodyPartList.value = (allPrefList as ArrayList<Preferences>?)!!
    }

    fun changeName(oldName: String, newName: String) {
        database4.value?.userDAO()?.updateName(oldName, newName)
    }

    fun changeQuestions(answer1: String, answer2: String, name: String) {
        database4.value?.userDAO()?.updateQuestions(answer1, answer2, name)
    }

    fun updatePreferences() {
        for (pref in bodyPartList.value!!) {
            database5.value?.preferencesDAO()?.updatePreferences(pref.name, pref.isChecked)
        }
    }

    fun getWeekExercises(time: Long) {
        val exList = database1.value?.exerciseDAO()?.getDateEx(time)
        exercisesNames.value = (exList as ArrayList<Exercise>?)!!
        Log.d("Getting Those Days!", "${exercisesNames.value}")
    }
}