package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeeklyPlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeeklyPlannerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerViewAdapter: WeekAdapter
    lateinit var recyclerViewManager: GridLayoutManager
    lateinit var list_recyclerView: RecyclerView


    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_YEAR, 1)
        val daysOfWeek = arrayOf(
            R.id.day_text2 to R.id.exercises2,
            R.id.day_text3 to R.id.exercises3,
            R.id.day_text4 to R.id.exercises4,
            R.id.day_text5 to R.id.exercises5,
            R.id.day_text6 to R.id.exercises6,
            R.id.day_text7 to R.id.exercises7
        )

        val times = ArrayList<Long>()

        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())

        daysOfWeek.forEachIndexed { index, (dayTextId, exercisesTextId) ->
            val dayName = dateFormat.format(currentDate.time)

            val startTime = currentDate.apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            // Update day text
            view.findViewById<TextView>(dayTextId).text = dayName

            // Get exercises for the day
            viewModel.getWeekExercises(startTime)
            times.add(startTime)
            val exercises = viewModel.exercisesNames.value!!
            var exerciseText = ""
            for (exercise in exercises) {
                exerciseText += "${exercise.name.split(" ").joinToString(" ") { it.capitalize() }} \n"
            }

            view.findViewById<TextView>(exercisesTextId).text = exerciseText

            // Move to the next day
            currentDate.add(Calendar.DAY_OF_YEAR, 1)
        }

        val currentDate2 = Calendar.getInstance()
        val dateFormat2 = SimpleDateFormat("EEEE", Locale.getDefault())
        val dayName2 = dateFormat2.format(currentDate2.time)
        Log.d("Day Name", dayName2)
        val startTime2 = currentDate2.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        viewModel.getWeekExercises(startTime2)
        val recyclerExercises = viewModel.exercisesNames.value!!

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
            bundle.putSerializable("selectedDate", startTime2)

            findNavController().navigate(R.id.action_weeklyPlannerFragment_to_completeFragment, bundle)
        }

        list_recyclerView = view.findViewById(R.id.todayrecyclerview)
        recyclerViewManager = GridLayoutManager(context, 2)
        recyclerViewAdapter = WeekAdapter(recyclerExercises, click)

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
        return inflater.inflate(R.layout.fragment_weekly_planner, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeeklyPlannerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeeklyPlannerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}