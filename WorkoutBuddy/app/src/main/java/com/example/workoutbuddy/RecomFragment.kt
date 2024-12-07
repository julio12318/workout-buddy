package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecomFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerViewAdapter: ExerciseAdapter
    lateinit var recyclerViewManager: GridLayoutManager
    lateinit var list_recyclerView: RecyclerView

    val viewModel: WorkoutBuddyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val partList = viewModel.bodyParts.value!!
        Log.d("Cool Beans 3", "${partList.size}")
        for (part in partList) {
            Log.d("Made it to Phase 1", "part")
            viewModel.getPreferences(part)
        }
        viewModel.getAllPreferences()

        val prefList = viewModel.bodyPartList.value!!
        Log.d("Gottem 1", "${prefList.size}")
        val randomNumbers = mutableSetOf<Int>()


        while (randomNumbers.size < 1) {
            // Generate a random index
            val randomIndex = Random.nextInt(prefList.size)
            randomNumbers.add(randomIndex)  // Ensure uniqueness by adding to a set
        }

        // Convert the set to a list if needed
        val randomIndicesList = randomNumbers.toList()

        val prefListRecom = ArrayList<Preferences>()

        for (index in randomIndicesList) {
            prefListRecom.add(prefList[index])
        }

        Log.d("Gottem 2", "${prefListRecom.size}")

        Log.d("Gottem 2.5", "${prefListRecom[0].name}")

        if (prefListRecom[0].isChecked) {
            val name = prefListRecom[0].name
            val nameCap = "${name.split(" ").joinToString(" ") { it.capitalize() }}"
            val line = "You Like ${nameCap}, Here are some exercises for that body part!"
            view.findViewById<TextView>(R.id.title_text).text = line
        }
        else {
            val name = prefListRecom[0].name
            val nameCap = "${name.split(" ").joinToString(" ") { it.capitalize() }}"
            val line = "Why don't you try some ${nameCap} exercises!"
            view.findViewById<TextView>(R.id.title_text).text = line
        }


        viewModel.startExercises("part", prefListRecom[0].name)

        viewModel.requestedWorkouts.observe(viewLifecycleOwner) {
            var recomExercises = viewModel.requestedWorkouts.value!!
            Log.d("Gottem 3", "${recomExercises.size}")


            val randomNumbers2 = mutableSetOf<Int>()

            while (randomNumbers2.size < 8 && randomNumbers2.size < recomExercises.size) {
                // Generate a random index
                val randomIndex = Random.nextInt(recomExercises.size)
                randomNumbers2.add(randomIndex)  // Ensure uniqueness by adding to a set
            }

            var specList = ArrayList<APIWorkoutObject>()
            for (index in randomNumbers2) {
                specList.add(recomExercises[index])
            }
            Log.d("Gottem 3.5", "${specList}")

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

                findNavController().navigate(R.id.action_recomFragment_to_exerciseSummaryFragment, bundle)
            }

            list_recyclerView = view.findViewById(R.id.recomrecyclerview)
            recyclerViewManager = GridLayoutManager(context, 2)
            recyclerViewAdapter = ExerciseAdapter(specList, click)

            list_recyclerView.apply {
                layoutManager = recyclerViewManager
                adapter = recyclerViewAdapter
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
        return inflater.inflate(R.layout.fragment_recom, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}