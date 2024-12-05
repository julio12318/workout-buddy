package com.example.workoutbuddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FocusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FocusFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var recyclerViewManager: LinearLayoutManager
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

        val bodyPartList = viewModel.bodyPartList.value!!
        Log.d("Preference List Size", "${bodyPartList.size}")


        list_recyclerView=view.findViewById<RecyclerView>(R.id.part_recyclerview)
        recyclerViewManager= LinearLayoutManager(context)
        recyclerViewAdapter=RecyclerViewAdapter(bodyPartList)

        viewModel.bodyPartList.observe(viewLifecycleOwner){
            recyclerViewAdapter.partList = it
            recyclerViewAdapter.notifyDataSetChanged()
        }

        list_recyclerView.apply {
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }

        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
            viewModel.updatePreferences()
            view.findNavController().navigate(R.id.action_focusFragment_to_profileFragment)
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
        return inflater.inflate(R.layout.fragment_focus, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FocusFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FocusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}