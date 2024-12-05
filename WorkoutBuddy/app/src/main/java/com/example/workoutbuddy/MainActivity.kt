package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel : WorkoutBuddyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("Check 1", "Made it to Step 1")
        viewModel.database1.value = ExerciseDB.getDBObject(applicationContext)
        viewModel.database2.value = CompletedExercisesDB.getDBObject(applicationContext)
        viewModel.database3.value = WeeklyScheduleDB.getDBObject(applicationContext)
        viewModel.database4.value = UserDB.getDBObject(applicationContext)
        viewModel.database5.value = PreferencesDB.getDBObject(applicationContext)

        viewModel.startExercises("", "")
        Log.d("Cool Beans 1", "Yes")
        viewModel.getParts()
        Log.d("Cool Beans 2", "Yes")


        findViewById<BottomNavigationView>(R.id.bottom_menu).setOnItemSelectedListener {
            if (it.itemId==R.id.to_rec_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_recomFragment)
                return@setOnItemSelectedListener true
            }else if (it.itemId==R.id.to_past_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_pastWorkoutFragment)
                return@setOnItemSelectedListener true
            } else if (it.itemId==R.id.to_week_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_weeklyPlannerFragment)
                return@setOnItemSelectedListener true
            } else if (it.itemId==R.id.to_future_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_planFragment)
                return@setOnItemSelectedListener true
            } else if (it.itemId==R.id.to_profile_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_profileFragment)
                return@setOnItemSelectedListener true
            }else{
                return@setOnItemSelectedListener true
            }
        }
    }

}