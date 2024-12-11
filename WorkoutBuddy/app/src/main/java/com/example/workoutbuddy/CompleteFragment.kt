package com.example.workoutbuddy

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompleteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompleteFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null


    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    var selectedImageUri: String? = null

    var selectedRating = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val options = ArrayList<String>()
        options.add("1 - Poor")
        options.add("2 - Below Average")
        options.add("3 - Average")
        options.add("4 - Above Average")
        options.add("5 - Excellent")

        val exIDString = bundle?.getString("exerciseID")!!
        val exID = UUID.fromString(exIDString)
        val part = bundle.getString("bodyPart")!!
        val equipment = bundle.getString("equipment")!!
        val gifUrl = bundle.getString("gifUrl")!!
        val name = bundle.getString("name")!!
        val target = bundle.getString("target")!!
        val secondaryMuscles = bundle.getString("secondaryMuscles")!!
        val instructions = bundle.getString("instructions")!!
        val time = bundle.getLong("selectedDate")!!


        // Set values to the views
        val nameCap = "${name.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_name).text = nameCap
        val bodyPartCap = "Body Part: ${part.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_part).text = bodyPartCap
        val equipCap = "Equipment: ${equipment.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_equipment).text = equipCap
        val targetCap = "Targeted Muscle:\n${target.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_target).text = targetCap
        val gifView = view.findViewById<ImageView>(R.id.exercise_image)
        Glide.with(view.context).load(gifUrl).into(gifView)
        val secMuscCap = "Additional Muslces:\n${secondaryMuscles.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_second).text = secMuscCap
        view.findViewById<TextView>(R.id.exercise_instructions).text = "Instructions: \n${instructions}"

        setupSpinner(view, options)

        // Add media button functionality
        view.findViewById<Button>(R.id.media_button).setOnClickListener {
            openGallery()
        }

        // Submit button functionality
        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
            if (selectedImageUri != null) {
                val completedExercise = CompletedExercises()
                completedExercise.bodyPart = part
                completedExercise.equipment = equipment
                completedExercise.gifUrl = gifUrl
                completedExercise.name = name
                completedExercise.target = target
                completedExercise.secondaryMuscles = secondaryMuscles
                completedExercise.instructions = instructions
                completedExercise.dateCreated = time
                completedExercise.imageURL = selectedImageUri!!
                if (selectedRating == 0) {
                    completedExercise.rating = "Poor"
                    completedExercise.ratingNum = 5
                } else if (selectedRating == 1) {
                    completedExercise.rating = "Below Average"
                    completedExercise.ratingNum = 4
                } else if (selectedRating == 2) {
                    completedExercise.rating = "Average"
                    completedExercise.ratingNum = 3
                } else if (selectedRating == 3) {
                    completedExercise.rating = "Above Average"
                    completedExercise.ratingNum = 2
                } else if (selectedRating == 4) {
                    completedExercise.rating = "Excellent"
                    completedExercise.ratingNum = 1
                }

                if (view.findViewById<RadioButton>(R.id.recommend).isChecked) {
                    completedExercise.recommend = true
                } else if (view.findViewById<RadioButton>(R.id.norecommend).isChecked) {
                    completedExercise.recommend = false
                }

                val min = view.findViewById<EditText>(R.id.minutes_text).text.toString()
                completedExercise.minutes = min

                val notes = view.findViewById<EditText>(R.id.notes_text).text.toString()
                completedExercise.notes = notes

                viewModel.addCompletedExercise(completedExercise)
                viewModel.deleteExercise(exID)
                viewModel.addPoints()
                viewModel.startOrGetUser()
                findNavController().navigate(R.id.action_global_weeklyPlannerFragment)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*" // Only images
        startActivityForResult(intent, 100) // 100 is a request code
    }

    private fun setupSpinner(view: View, options: List<String>) {
        val spinner = view.findViewById<Spinner>(R.id.spinner2)
        val choices = ArrayList<String>().apply {
            addAll(options)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, choices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                view?.let {
                    selectedRating = position
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            selectedImageUri = data?.data.toString()
            val imageView = view?.findViewById<ImageView>(R.id.exercise_image2)

            if (imageView != null && selectedImageUri != null) {
                Glide.with(requireContext()).load(selectedImageUri).into(imageView)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complete, container, false)
    }

    // Handle permissions result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                // Handle permission denial (maybe show a message to the user)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompleteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}