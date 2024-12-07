package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

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
        val exID = exIDString.let { UUID.fromString(it) }
        val part = bundle.getString("bodyPart")!!
        val equipment = bundle.getString("equipment")!!
        val gifUrl = bundle.getString("gifUrl")!!
        val name = bundle.getString("name")!!
        val target = bundle.getString("target")!!
        val secondaryMuscles = bundle.getString("secondaryMuscles")!!
        val instructions = bundle.getString("instructions")!!
        val time = bundle.getLong("selectedDate")!!

        Log.d("You Ready to finish this!","${time}")

        view.findViewById<TextView>(R.id.exercise_name).text = name
        view.findViewById<TextView>(R.id.exercise_part).text = part
        view.findViewById<TextView>(R.id.exercise_equipment).text = equipment
        view.findViewById<TextView>(R.id.exercise_target).text = target
        val gifView = view.findViewById<ImageView>(R.id.exercise_image)
        Glide.with(view.context).load(gifUrl).into(gifView)
        view.findViewById<TextView>(R.id.exercise_second).text = secondaryMuscles
        view.findViewById<TextView>(R.id.exercise_instructions).text = instructions

        setupSpinner(view, options)

        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
            val completedExercise = CompletedExercises()
            completedExercise.bodyPart = part
            completedExercise.equipment = equipment
            completedExercise.gifUrl = gifUrl
            completedExercise.name = name
            completedExercise.target = target
            completedExercise.secondaryMuscles = secondaryMuscles
            completedExercise.instructions = instructions
            completedExercise.dateCreated = time
            if (selectedRating == 0) {
                completedExercise.rating = "Poor"
            } else if (selectedRating == 1) {
                completedExercise.rating = "Below Average"
            } else if (selectedRating == 2) {
                completedExercise.rating = "Average"
            } else if (selectedRating == 3) {
                completedExercise.rating = "Above Average"
            } else if (selectedRating == 4) {
                completedExercise.rating = "Excellent"
            }

            if (view.findViewById<RadioButton>(R.id.recommend).isChecked) {
                completedExercise.recommend = true
            } else if (view.findViewById<RadioButton>(R.id.norecommend).isChecked) {
                completedExercise.recommend = false
            }

            val min = view.findViewById<EditText>(R.id.minutes_text).text.toString()
            completedExercise.minutes = min

            viewModel.addCompletedExercise(completedExercise)
            viewModel.deleteExercise(exID)
            findNavController().navigate(R.id.action_global_weeklyPlannerFragment)
        }
    }

    private fun setupSpinner(view: View, options: List<String>) {
        val spinner = view.findViewById<Spinner>(R.id.spinner2)
        val choices = ArrayList<String>()

        for (option in options) {
            choices.add(option)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, choices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Null check for the view parameter to prevent crashes
                view?.let {
                    selectedRating = position
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompleteFragment.
         */
        // TODO: Rename and change types and number of parameters
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