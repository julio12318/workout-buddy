package com.example.workoutbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InterviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InterviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.user.value

        val q1choice = user?.question1
        val q2choice = user?.question2

        if (q1choice == "once") {
            view.findViewById<RadioButton>(R.id.once_a_week).isChecked = true
        }
        else if (q1choice == "twice") {
            view.findViewById<RadioButton>(R.id.twice_a_week).isChecked = true
        }
        else if (q1choice == "more") {
            view.findViewById<RadioButton>(R.id.more_a_week).isChecked = true
        }

        view.findViewById<EditText>(R.id.editTextNumber).setText(q2choice)

        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
            var selection1: String
            if (view.findViewById<RadioButton>(R.id.once_a_week).isChecked) {
                selection1 = "once"
            } else if (view.findViewById<RadioButton>(R.id.twice_a_week).isChecked) {
                selection1 = "twice"
            } else if (view.findViewById<RadioButton>(R.id.more_a_week).isChecked) {
                selection1 = "more"
            } else {
                selection1 = "none"
            }
            var selection2 = view.findViewById<EditText>(R.id.editTextNumber).text.toString()
            if (user != null) {
                viewModel.changeQuestions(selection1, selection2, user.name)
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
        return inflater.inflate(R.layout.fragment_interview, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InterviewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InterviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}