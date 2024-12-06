package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseExerciseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerViewAdapter: ExerciseAdapter
    lateinit var recyclerViewManager: LinearLayoutManager
    lateinit var list_recyclerView: RecyclerView

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    private var previouslySelectedPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parts = viewModel.bodyParts.value!!
        setupSpinner(view, parts)
        var date = Date()

        val bundle = arguments
        if (bundle != null) {
            // Retrieve values by their keys

            date = bundle.getSerializable("selectedDate") as Date
            val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(date)

            view.findViewById<TextView>(R.id.current_date_text).text = "Choose Exercise For: ${formattedDate}"

        }

        val exercises = viewModel.requestedWorkouts.value!!

        val click: (APIWorkoutObject) -> Unit = { exercise ->
            val bundle = Bundle()
            bundle.putString("bodyPart", exercise.bodyPart)
            bundle.putString("equipment", exercise.equipment)
            bundle.putString("gifUrl", exercise.gifUrl)
            bundle.putString("workoutID", exercise.workoutID)
            bundle.putString("name", exercise.name)
            bundle.putString("target", exercise.target)
            bundle.putStringArrayList("secondaryMuscles", exercise.secondaryMuscles)
            bundle.putStringArrayList("instructions", exercise.instructions)
            bundle.putSerializable("selectedDate", date)

            findNavController().navigate(R.id.action_chooseExerciseFragment_to_quickSummaryFragment, bundle)
        }

        list_recyclerView = view.findViewById(R.id.exerciserecyclerview)
        recyclerViewManager = LinearLayoutManager(context)
        recyclerViewAdapter = ExerciseAdapter(exercises, click)

        viewModel.requestedWorkouts.observe(viewLifecycleOwner) {
            recyclerViewAdapter.exercisesList = it
            recyclerViewAdapter.notifyDataSetChanged()
        }

        list_recyclerView.apply {
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }
    }

    private fun setupSpinner(view: View, parts: List<String>) {
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val choices = ArrayList<String>()

        for (part in parts) {
            choices.add(part)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, choices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Null check for the view parameter to prevent crashes
                view?.let {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    viewModel.startExercises("part", selectedItem)
                    previouslySelectedPosition = position  // Save the selected position
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
        return inflater.inflate(R.layout.fragment_choose_exercise, container, false)
    }

    // Ensure that the Spinner selection is preserved when the fragment is resumed
    override fun onResume() {
        super.onResume()
        val spinner = view?.findViewById<Spinner>(R.id.spinner)
        spinner?.setSelection(previouslySelectedPosition)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChooseExerciseFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChooseExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
