package com.example.workoutbuddy

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = "54e13462d4mshf9b6ae3902b1497p1ca2f5jsn5b7a600654c0"



class APIManager (var exerciseViewModel: WorkoutBuddyViewModel){

    private val apiURL = "https://exercisedb.p.rapidapi.com/"

    interface WorkoutService {
        // Refer to https://v2.exercisedb.io/docs#tag/exercises for information about each API Call
        @Headers(
            "x-rapidapi-key: $apiKey",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises?")
        fun getExercises(
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>

        @Headers(
            "x-rapidapi-key: $apiKey",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises/bodyPartList?")
        fun getBodyParts(
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>

        @Headers(
            "x-rapidapi-key: $apiKey",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises/target/{target}?")
        fun getExercisesByMuscle(
            @Path("target") muscle : String,
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>

        @Headers(
            "x-rapidapi-key: $apiKey",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises/bodyPart/{bodyPart}?")
        fun getExercisesByBodyPart(
            @Path("bodyPart") bodyPart : String,
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>

        @Headers(
            "x-rapidapi-key: $apiKey",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises/equipment/{type}?")
        fun getExercisesByEquipment(
            @Path("type") equipment : String,
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>

        @Headers(
            "x-rapidapi-key: $apiKey",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises/exercise/{name}?")
        fun getExercisesByName(
            @Path("name") exerciseName : String,
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>

        @Headers(
            "x-rapidapi-key: $apiKey",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises/exercise/{id}?")
        fun getExercisesById(
            @Path("id") exerciseId : String,
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>
    }

    fun fetchExercises(limit : Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getExercises(limit, offset)
        call.enqueue(WorkoutRequestCallback())
    }

    fun fetchBodyParts(limit : Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getBodyParts(limit, offset)
        call.enqueue(BodyPartRequestCallback())
    }

    // Valid inputs for "muscle": abductors, abs, adductors, biceps, calves, cardiovascular system,
    // delts, forearms, glutes, hamstrings, lats, levator scapulae, pectorals, quads,
    // serratus anterior, spine, traps, triceps, upper back
    fun fetchExercisesByMuscle(muscle : String, limit: Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getExercisesByMuscle(muscle, limit, offset)
        call.enqueue(WorkoutRequestCallback())
    }

    // Valid inputs for "bodyPart": back, cardio, chest, lower arms, lower legs, neck, shoulders,
    // upper arms, upper legs, waist
    fun fetchExercisesByBodyPart(bodyPart : String, limit: Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getExercisesByBodyPart(bodyPart, limit, offset)
        call.enqueue(WorkoutRequestCallback())
    }

    // Valid inputs for equipment: assisted, band, barbell, body weight, bosu ball, cable, dumbbell,
    // elliptical machine, ez barbell, hammer, kettlebell, leverage machine, medicine ball,
    // olympic barbell, resistance band, roller, rope, skierg machine, sled machine, smith machine,
    // stability ball, stationary bike, stepmill machine, tire, trap bar, upper body ergometer,
    // weighted, wheel roller
    fun fetchExercisesByEquipment(equipment : String, limit: Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getExercisesByEquipment(equipment, limit, offset)
        call.enqueue(WorkoutRequestCallback())
    }

    fun fetchExercisesByName(name : String, limit: Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getExercisesByName(name, limit, offset)
        call.enqueue(WorkoutRequestCallback())
    }

    fun fetchExercisesById(id : String, limit: Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getExercisesById(id, limit, offset)
        call.enqueue(WorkoutRequestCallback())
    }

    fun addRequestedWorkouts(json : String) {
        val data = JSONArray(json)
        val workouts = ArrayList<APIWorkoutObject>()
        for (i in 0..<data.length()) {
            val workoutData = data.getJSONObject(i)
            val workoutObject = APIWorkoutObject()
            workoutObject.bodyPart = workoutData.getString("bodyPart")
            workoutObject.equipment = workoutData.getString("equipment")
            workoutObject.gifUrl = workoutData.getString("gifUrl")
            workoutObject.workoutID = workoutData.getString("id")
            workoutObject.name = workoutData.getString("name")
            workoutObject.target = workoutData.getString("target")
            var dataBuffer = workoutData.getJSONArray("secondaryMuscles")
            for(j in 0..<dataBuffer.length()) {
                workoutObject.secondaryMuscles.add(dataBuffer.getString(j))
            }
            dataBuffer = workoutData.getJSONArray("instructions")
            for(j in 0..<dataBuffer.length()) {
                workoutObject.instructions.add(dataBuffer.getString(j))
            }
            workouts.add(workoutObject)
        }
        exerciseViewModel.requestedWorkouts.value = workouts
    }

    fun addBodyParts(json : String) {
        val data = JSONArray(json)
        val bodyParts = ArrayList<String>()
        for (i in 0..<data.length()) {
            val bodyPart = data.getString(i)
            bodyParts.add(bodyPart)
        }
        exerciseViewModel.bodyParts.value = bodyParts
        if (exerciseViewModel.finish.value != 1) {
            exerciseViewModel.finish.value = 1
        }
    }

    inner class WorkoutRequestCallback : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val responseString = body.string()
                    addRequestedWorkouts(responseString)
                }
            } else {
                Log.e("apicall", response.errorBody()!!.string())
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("apicall", "Request failed: ${t.message}")
        }
    }

    inner class BodyPartRequestCallback : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val responseString = body.string()
                    addBodyParts(responseString)
                }
            }
            else {
                Log.e("apicall", response.errorBody()!!.string())
            }

        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("apicall", "Request failed: ${t.message}")
        }
    }
}
