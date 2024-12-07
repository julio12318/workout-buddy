package com.example.workoutbuddy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

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

    val parentObjects = MutableLiveData<ArrayList<ParentItem>>()

    val finish = MutableLiveData<Int>()




    init {
        apiManager.value = APIManager(this)
        requestedWorkouts.value = ArrayList<APIWorkoutObject>()
        bodyParts.value = ArrayList<String>()
        bodyPartList.value = ArrayList<Preferences>()
        exercisesNames.value = ArrayList<Exercise>()

        parentObjects.value = ArrayList<ParentItem>()
        finish.value = 0
    }

    fun startExercises(method: String, attribute: String) {
        if (method == "part") {
            Log.d("Gottem 2.6", attribute)
            apiManager.value?.fetchExercisesByBodyPart(attribute)
        }
//        apiManager.value?.fetchExercisesByMuscle("serratus anterior");
    }

    fun getParts() {
        Log.d("How Lucky", "Let's Go")
        apiManager.value?.fetchBodyParts()
        Log.d("Announcement", "Body Parts Fetched!")
        Log.d("Size", "Size of List: ${bodyParts.value?.size}")

    }

    fun addExercise(exercise: Exercise) {
        database1.value?.exerciseDAO()?.insert(exercise)
        Log.d("Exercise Time", "${exercise.dateCreated}")
    }

    fun deleteExercise(id: UUID) {
        database1.value?.exerciseDAO()?.delete(id)
    }

    fun addCompletedExercise(completedExercise: CompletedExercises) {
        database2.value?.completedExercisesDAO()?.insert(completedExercise)
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

    fun updateInitiate() {
        finish.value = 1
    }

    fun addPoints() {
        val user = user.value!!
        val currentPoints = user.points
        Log.d("Current Points", currentPoints.toString())
        database4.value?.userDAO()?.updatePoints(currentPoints + 100)
    }

    fun getQualities(quality: String) {
        if (quality == "Date") {
            val dateList = database2.value?.completedExercisesDAO()?.getCompDate()
            val dates = (dateList as ArrayList<Long>?)!!
            val createParents = ArrayList<ParentItem>()
            for (date in dates){
                Log.d("Long Date", "${date}")
                val dateSt = date.toString()
                val qualList = database2.value?.completedExercisesDAO()?.getDate(date)
                val qL2 = (qualList as ArrayList<CompletedExercises>)
                val pItem = ParentItem()
                pItem.title = dateSt
                pItem.comExerciseList = qL2
                createParents.add(pItem)
            }
            parentObjects.value = createParents

        }
        else if (quality == "Part") {
            val partLt = database2.value?.completedExercisesDAO()?.getCompPart()
            val items = (partLt as ArrayList<String>?)!!
            val createParents = ArrayList<ParentItem>()
            for (item in items){
                val qualList = database2.value?.completedExercisesDAO()?.getPart(item)
                val qL2 = (qualList as ArrayList<CompletedExercises>)
                val pItem = ParentItem()
                pItem.title = item
                pItem.comExerciseList = qL2
                createParents.add(pItem)
            }
            parentObjects.value = createParents
        }
        else if (quality == "Rating") {
            val ratingLt = database2.value?.completedExercisesDAO()?.getCompRating()
            val items = (ratingLt as ArrayList<String>?)!!
            val createParents = ArrayList<ParentItem>()
            for (item in items){
                val qualList = database2.value?.completedExercisesDAO()?.getRating(item)
                val qL2 = (qualList as ArrayList<CompletedExercises>)
                val pItem = ParentItem()
                pItem.title = item
                pItem.comExerciseList = qL2
                createParents.add(pItem)
            }
            parentObjects.value = createParents
        }
    }
}