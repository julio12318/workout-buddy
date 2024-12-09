package com.example.workoutbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseSummaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val bodyPart = bundle?.getString("bodyPart")!!
        val equipment = bundle.getString("equipment")!!
        val gifUrl = bundle.getString("gifUrl")!!
        val name = bundle.getString("name")!!
        val target = bundle.getString("target")!!
        val secondaryMuscles = bundle.getStringArrayList("secondaryMuscles")!!
        val instructions = bundle.getStringArrayList("instructions")!!

        var secMuscles = ""
        for (muscle in secondaryMuscles) {
            secMuscles += "${muscle} \n "
        }

        var instruct = ""
        for (ins in instructions) {
            instruct += "${ins}\n"
        }

        val nameCap = "${name.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_name).text = nameCap


        val bodyPartCap = "Body Part: ${bodyPart.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_part).text = bodyPartCap
        val equipCap = "Equipment: ${equipment.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_equipment).text = equipCap
        val secMuscCap = "Additional Muslces:\n${secMuscles.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_group).text = secMuscCap
        val gifView = view.findViewById<ImageView>(R.id.exercise_image)
        Glide.with(view.context).load(gifUrl).into(gifView)
        val targetCap = "Targeted Muscle:\n${target.split(" ").joinToString(" ") { it.capitalize() }}"
        view.findViewById<TextView>(R.id.exercise_description).text = targetCap

        view.findViewById<TextView>(R.id.exercise_instructions).text = "Instructions:\n${instruct}"

        view.findViewById<Button>(R.id.add_button).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("bodyPart", bodyPart)
            bundle.putString("equipment", equipment)
            bundle.putString("gifUrl", gifUrl)
            bundle.putString("name", name)
            bundle.putString("target", target)
            bundle.putString("secondaryMuscles", secMuscles)
            bundle.putString("instructions", instruct)

            view.findNavController().navigate(R.id.action_exerciseSummaryFragment_to_addExerciseFragment, bundle)

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
        return inflater.inflate(R.layout.fragment_exercise_summary, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExerciseSummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseSummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}