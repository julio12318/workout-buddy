package com.example.workoutbuddy

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.view.View

class MainActivity : AppCompatActivity() {
    val viewModel: WorkoutBuddyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set padding to avoid overlapping UI with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }


        // Initialize the databases in ViewModel
        viewModel.database1.value = ExerciseDB.getDBObject(applicationContext)
        viewModel.database2.value = CompletedExercisesDB.getDBObject(applicationContext)
        viewModel.database3.value = WeeklyScheduleDB.getDBObject(applicationContext)
        viewModel.database4.value = UserDB.getDBObject(applicationContext)
        viewModel.database5.value = PreferencesDB.getDBObject(applicationContext)

        viewModel.getParts()
        viewModel.startOrGetUser()

        // Set up bottom navigation and fragment navigation
        findViewById<BottomNavigationView>(R.id.bottom_menu).setOnItemSelectedListener {
            if (it.itemId == R.id.to_rec_frag) {
                Log.d("MainActivity", "Navigating to RecomFragment")
                findNavController(R.id.frag).navigate(R.id.action_global_recomFragment)
                return@setOnItemSelectedListener true
            } else if (it.itemId == R.id.to_past_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_pastWorkoutFragment)
                return@setOnItemSelectedListener true
            } else if (it.itemId == R.id.to_week_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_weeklyPlannerFragment)
                return@setOnItemSelectedListener true
            } else if (it.itemId == R.id.to_future_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_planFragment)
                return@setOnItemSelectedListener true
            } else if (it.itemId == R.id.to_profile_frag) {
                findNavController(R.id.frag).navigate(R.id.action_global_profileFragment)
                return@setOnItemSelectedListener true
            }
            return@setOnItemSelectedListener false
        }

        findNavController(R.id.frag).addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loadingFragment) {
                findViewById<BottomNavigationView>(R.id.bottom_menu).visibility = View.GONE
            } else {
                findViewById<BottomNavigationView>(R.id.bottom_menu).visibility = View.VISIBLE
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with functionality
            } else {
                // Permission denied, handle accordingly
            }
        }
    }

}
