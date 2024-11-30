package com.example.workoutbuddy

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

class APIManager (var movieViewModel: WorkoutBuddyViewModel){
    private val apiURL = "https://exercisedb.p.rapidapi.com/"
    private val apiKey = "3a77f2ee09mshe8f0ccb07284438p1685f2jsn81190152cda8"

    interface WorkoutService {
        @Headers(
            "x-rapidapi-key: 3a77f2ee09mshe8f0ccb07284438p1685f2jsn81190152cda8",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises?")
        fun getExercises(
            @Query("limit") limit : Int,
            @Query("offset") offset : Int,
        ) : Call<ResponseBody>

        @Headers(
            "x-rapidapi-key: 3a77f2ee09mshe8f0ccb07284438p1685f2jsn81190152cda8",
            "x-rapidapi-host: exercisedb.p.rapidapi.com"
        )
        @GET("exercises/target/{target}?")
        fun getExercisesByMuscle(
            @Path("target") muscle : String,
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

    fun fetchExercisesByMuscle(muscle : String, limit: Int = 10, offset: Int = 0) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .build()
        val service = retrofit.create(WorkoutService::class.java)
        val call = service.getExercisesByMuscle(muscle, limit, offset)
        call.enqueue(WorkoutRequestCallback())
    }


    inner class WorkoutRequestCallback : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    // TODO: Add stuff here
                    Log.d("asdf", body.string())
                }
            } else {
                Log.e("apicall", response.errorBody()!!.string())
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("apicall", "Request failed: ${t.message}")
        }
    }
}
