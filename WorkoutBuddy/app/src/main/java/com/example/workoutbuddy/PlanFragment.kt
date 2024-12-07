package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
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
 * Use the [PlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    // Declare a variable to hold the selected date
    private var selectedDate: Calendar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views
        val calendarView = view.findViewById<CalendarView>(R.id.plan_calendar)
        val dateTextView = view.findViewById<TextView>(R.id.date_text)

        // Get the current date
        val currentDate = Calendar.getInstance()
        selectedDate = Calendar.getInstance()
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
                        "${selectedCalendar.get(Calendar.DAY_OF_MONTH)}, ${selectedCalendar.get(Calendar.YEAR)}"
                dateTextView.text = formattedDate
            }
        }

        view.findViewById<Button>(R.id.exercise_button).setOnClickListener {
            // Use the stored selected date
            selectedDate?.let { date ->
                val bundle = Bundle().apply {
                    putSerializable("selectedDate", date.time) // Put the Date object
                }

                Log.d("PlanFragment", "Passing selectedDate: ${date.time}")
                view.findNavController().navigate(R.id.action_planFragment_to_chooseExerciseFragment, bundle)
            } ?: run {
                Toast.makeText(requireContext(), "Please select a date first", Toast.LENGTH_SHORT).show()
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
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}