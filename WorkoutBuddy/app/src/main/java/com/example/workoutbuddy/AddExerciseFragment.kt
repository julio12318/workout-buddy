package com.example.workoutbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class AddExerciseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    private var selectedDate: Calendar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val bodyPart = bundle?.getString("bodyPart")!!
        val equipment = bundle.getString("equipment")!!
        val gifUrl = bundle.getString("gifUrl")!!
        val name = bundle.getString("name")!!
        val target = bundle.getString("target")!!
        val secondaryMuscles = bundle.getString("secondaryMuscles")!!
        val instructions = bundle.getString("instructions")!!

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val dateTextView = view.findViewById<TextView>(R.id.date_to_add)

        // Get the current date
        val currentDate = Calendar.getInstance()
        calendarView.minDate = currentDate.timeInMillis // Restrict past dates

        // Set the initial date text to today's date
        val initialDate = "${currentDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())}, " +
                "${currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())} " +
                "${currentDate.get(Calendar.DAY_OF_MONTH)}, ${currentDate.get(Calendar.YEAR)}"
        dateTextView.text = initialDate

        // Handle date change
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            // Ensure the selected date is not in the past
            if (selectedCalendar.before(currentDate)) {
                Toast.makeText(requireContext(), "Cannot select a past date", Toast.LENGTH_SHORT).show()
            } else {
                // Save the selected date to the variable
                selectedDate = selectedCalendar

                val formattedDate = "${selectedCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())}, " +
                        "${selectedCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())} " +
                        "${selectedCalendar.get(Calendar.DAY_OF_MONTH)}, ${selectedCalendar.get(
                            Calendar.YEAR)}"
                dateTextView.text = formattedDate
            }
        }

        view.findViewById<Button>(R.id.add_button).setOnClickListener {
            if (selectedDate != null) {
                selectedDate!!.set(Calendar.HOUR_OF_DAY, 0)
                selectedDate!!.set(Calendar.MINUTE, 0)
                selectedDate!!.set(Calendar.SECOND, 0)
                selectedDate!!.set(Calendar.MILLISECOND, 0)
                val date = selectedDate!!.time
                val timeLong = date.time

                val exercise = Exercise()
                exercise.bodyPart = bodyPart
                exercise.equipment = equipment
                exercise.gifUrl = gifUrl
                exercise.name = name
                exercise.target = target
                exercise.secondaryMuscles = secondaryMuscles
                exercise.instructions = instructions
                exercise.dateCreated = timeLong
                viewModel.addExercise(exercise)
                view.findNavController().navigate(R.id.action_global_recomFragment)
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
        return inflater.inflate(R.layout.fragment_add_exercise, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}