package com.example.workoutbuddy

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

class APIManager(var workoutViewModel: WorkoutBuddyViewModel) {
    private val apiURL = "https://exercisedb.p.rapidapi.com/"
    private val apiKey = "b511a2ff85msh8ba1b06bf526e8fp165b79jsn670324e2796e"
    private val apiHost = "exercisedb.p.rapidapi.com"

    interface ExerciseService {
        @GET("exercises")
        fun getExercises(): Call<ResponseBody>
    }

    fun fetchExercises() {
        // Add headers dynamically via an Interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-rapidapi-key", apiKey)
                    .addHeader("x-rapidapi-host", apiHost)
                    .build()
                chain.proceed(request)
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .client(client)
            .build()

        val service = retrofit.create(ExerciseService::class.java)
        val call = service.getExercises()
        call.enqueue(ExerciseCallback())
    }

    inner class ExerciseCallback : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val jsonString = body.string()
                    Log.d("apicall", jsonString)
                }
            } else {
                Log.e("apicall", "API returned an error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("apicall", "Request failed: ${t.message}")
        }
    }
}
