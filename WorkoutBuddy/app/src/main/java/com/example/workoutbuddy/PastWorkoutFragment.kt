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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PastWorkoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PastWorkoutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerViewAdapter: ParentPastAdapter
    lateinit var recyclerViewManager: LinearLayoutManager
    lateinit var list_recyclerView: RecyclerView


    val viewModel: WorkoutBuddyViewModel by activityViewModels()
    private var previouslySelectedPosition: Int = 0

    private var whichSort: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("position", "${previouslySelectedPosition}")
        Log.d("sorted", "${whichSort}")

        val options = ArrayList<String>()
        options.add("Date")
        options.add("Part")
        options.add("Rating")
        setupSpinner(view, options)

        val spinner = view.findViewById<Spinner>(R.id.spinner3)
        val selectedString = spinner.selectedItem as String
        viewModel.getQualities(selectedString)


        val parents = viewModel.parentObjects.value!!
        Log.d("parents", "${parents}")

        val click: (CompletedExercises) -> Unit = { exercise ->
            val bundle = Bundle()
            bundle.putString("bodyPart", exercise.bodyPart)
            bundle.putString("equipment", exercise.equipment)
            bundle.putString("gifUrl", exercise.gifUrl)
            bundle.putString("name", exercise.name)
            bundle.putString("target", exercise.target)
            bundle.putString("secondaryMuscles", exercise.secondaryMuscles)
            bundle.putString("instructions", exercise.instructions)
            bundle.putLong("selectedDate", exercise.dateCreated)
            bundle.putBoolean("recommend", exercise.recommend)
            bundle.putString("minutes", exercise.minutes)
            bundle.putString("rating", exercise.rating)
            bundle.putString("imageURL", exercise.imageURL)


            findNavController().navigate(R.id.action_pastWorkoutFragment_to_pastWorkoutSummaryFragment, bundle)
        }

        list_recyclerView = view.findViewById(R.id.titlerecyclerview)
        recyclerViewManager = LinearLayoutManager(context)
        recyclerViewAdapter = ParentPastAdapter(parents, whichSort, click)

        viewModel.parentObjects.observe(viewLifecycleOwner) {
            Log.d("This is the option!", "${previouslySelectedPosition}")
            Log.d("This is the whichSort!", "${whichSort}")
            recyclerViewAdapter.itemsList = it
            recyclerViewAdapter.isDate = whichSort
            recyclerViewAdapter.notifyDataSetChanged()
        }

        list_recyclerView.apply {
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }
    }

    private fun setupSpinner(view: View, options: List<String>) {
        val spinner = view.findViewById<Spinner>(R.id.spinner3)
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
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    previouslySelectedPosition = position
                    if (previouslySelectedPosition == 0) {
                        whichSort = true
                    }
                    else {
                        whichSort = false
                    }
                    viewModel.getQualities(selectedItem)// Save the selected position
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
        return inflater.inflate(R.layout.fragment_past_workout, container, false)
    }

    override fun onResume() {
        super.onResume()
        val spinner = view?.findViewById<Spinner>(R.id.spinner3)
        spinner?.setSelection(previouslySelectedPosition)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PastWorkoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PastWorkoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}