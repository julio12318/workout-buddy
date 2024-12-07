package com.example.workoutbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuickSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuickSummaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments

        val part = bundle?.getString("bodyPart")!!
        val equipment = bundle.getString("equipment")!!
        val gifUrl = bundle.getString("gifUrl")!!
        val id = bundle.getString("workoutID")!!
        val name = bundle.getString("name")!!
        val target = bundle.getString("target")!!
        val group = bundle.getStringArrayList("secondaryMuscles")!!
        val instructions = bundle.getStringArrayList("instructions")!!
        val sendDate = bundle.getSerializable("selectedDate") as Date


        var secMuscles = ""
        for (muscle in group) {
            secMuscles += "${muscle}\n "
        }

        var instruct = ""
        for (ins in instructions) {
            instruct += "${ins}\n "
        }

        val calendar = Calendar.getInstance()
        calendar.time = sendDate

        // Set the time part to 00:00:00.000
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Get the new Date object with the time set to 00:00:00.000
        val updatedSendDate = calendar.time

        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(updatedSendDate)

        view.findViewById<TextView>(R.id.exercise_date).text = "Choose Exercise For: ${formattedDate}"

        view.findViewById<TextView>(R.id.exercise_name).text = name
        view.findViewById<TextView>(R.id.exercise_part).text = part
        view.findViewById<TextView>(R.id.exercise_group).text = secMuscles
        view.findViewById<TextView>(R.id.exercise_description).text = target
        view.findViewById<TextView>(R.id.exercise_instructions).text = instruct
        val gifView = view.findViewById<ImageView>(R.id.exercise_image)
        Glide.with(view.context).load(gifUrl).into(gifView)

        val timeFloat = updatedSendDate.time




        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
            val exercise = Exercise()
            exercise.bodyPart = part
            exercise.equipment = equipment
            exercise.gifUrl = gifUrl
            exercise.name = name
            exercise.target = target
            exercise.secondaryMuscles = secMuscles
            exercise.instructions = instruct
            exercise.dateCreated = timeFloat
            viewModel.addExercise(exercise)
            view.findNavController().navigate(R.id.action_global_planFragment)
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
        return inflater.inflate(R.layout.fragment_quick_summary, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuickSummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuickSummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}