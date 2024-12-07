package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * Use the [DayPlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayPlanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerViewAdapter: WeekAdapter
    lateinit var recyclerViewManager: LinearLayoutManager
    lateinit var list_recyclerView: RecyclerView

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val time = bundle?.getLong("dayTime")!!
        Log.d("OK, so we here", time.toString())
        val date = Date(time)
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        view.findViewById<TextView>(R.id.current_date).text = formattedDate
        viewModel.getWeekExercises(time)
        val exercises = viewModel.exercisesNames.value!!

        val click: (Exercise) -> Unit = { exercise ->
            val bundle = Bundle()
            bundle.putString("exerciseID", exercise.id.toString())
            bundle.putString("bodyPart", exercise.bodyPart)
            bundle.putString("equipment", exercise.equipment)
            bundle.putString("gifUrl", exercise.gifUrl)
            bundle.putString("name", exercise.name)
            bundle.putString("target", exercise.target)
            bundle.putString("secondaryMuscles", exercise.secondaryMuscles)
            bundle.putString("instructions", exercise.instructions)
            bundle.putSerializable("selectedDate", time)
            Log.d("You clicked me!", time.toString())

            findNavController().navigate(R.id.action_dayPlanFragment_to_completeFragment, bundle)
        }

        list_recyclerView = view.findViewById(R.id.weekrecyclerview)
        recyclerViewManager = LinearLayoutManager(context)
        recyclerViewAdapter = WeekAdapter(exercises, click)

        viewModel.exercisesNames.observe(viewLifecycleOwner) {
            recyclerViewAdapter.weekList = it
            recyclerViewAdapter.notifyDataSetChanged()
        }

        list_recyclerView.apply {
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
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
        return inflater.inflate(R.layout.fragment_day_plan, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DayPlanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DayPlanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}