package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PastWorkoutSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PastWorkoutSummaryFragment : Fragment() {
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
        val name = bundle.getString("name")!!
        val target = bundle.getString("target")!!
        val group = bundle.getString("secondaryMuscles")!!
        val instructions = bundle.getString("instructions")!!
        val date = bundle.getLong("selectedDate")
        val recommend = bundle.getBoolean("recommend")
        val rating = bundle.getString("rating")
        val minutes = bundle.getString("minutes")
        val imageURL = bundle.getString("imageURL")

        Log.d("Gotcha Image", imageURL!!)

        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)

        val gifView = view.findViewById<ImageView>(R.id.exercise_image)
        Glide.with(view.context).load(gifUrl).into(gifView)

        view.findViewById<TextView>(R.id.exercise_date).text = formattedDate
        val imageView = view.findViewById<ImageView>(R.id.exercise_image2)


        Glide.with(requireContext())
            .load(imageURL)
            .placeholder(R.drawable.profile) // Show a placeholder if image is missing
            .error(R.drawable.profile)       // Show an error image if the file is deleted
            .into(imageView)

        val nameCap = "${name.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_name).text = nameCap
        val partCap = "${part.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_part).text = partCap
        val groupCap = "${group.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_group).text = groupCap
        val targetCap = "${target.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_description).text = targetCap
        val instructCap = "${instructions.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_instructions).text = instructCap
        view.findViewById<TextView>(R.id.exercise_rating).text = "Rating: $rating}"
        view.findViewById<TextView>(R.id.exercise_time).text = "You Worked Out for ${minutes} minutes"
        if (recommend) {
            view.findViewById<TextView>(R.id.exercise_recommend).text = "Recommend"
        }
        else {
            view.findViewById<TextView>(R.id.exercise_recommend).text = "Don't Recommend"
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
        return inflater.inflate(R.layout.fragment_past_workout_summary, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PastWorkoutSummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PastWorkoutSummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}