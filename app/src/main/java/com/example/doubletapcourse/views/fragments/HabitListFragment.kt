package com.example.doubletapcourse.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.doubletapcourse.R
import com.example.doubletapcourse.databinding.FragmentHabitListBinding
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.utlis.ExtraConstants
import com.example.doubletapcourse.views.adapter.HabitAdapter
import com.example.doubletapcourse.views.viewModel.HabitListViewModel


class HabitListFragment : Fragment() {
    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HabitAdapter

    private val viewModel: HabitListViewModel by viewModels()
    private lateinit var habits: LiveData<MutableList<Habit>>




    companion object {
        @JvmStatic
        fun newInstance(isPositiveHabits: Boolean) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ExtraConstants.IS_POSITIVE_HABITS, isPositiveHabits)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val isPositiveHabits = requireArguments().getBoolean(ExtraConstants.IS_POSITIVE_HABITS)

        if (isPositiveHabits)
            habits = viewModel.positiveHabits
        else
            habits = viewModel.negativeHabits

        habits.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(habits.value!!)
    }



    private fun setRecyclerView(
        habitItems: MutableList<Habit>,

        ) {
        adapter = HabitAdapter(habitItems) { habit: Habit, position: Int ->

            parentFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    AddHabitFragment.newInstance(ExtraConstants.EDIT_HABIT, habit)
                ).hide(parentFragmentManager.findFragmentById(R.id.fragment_container)!!).commit()

        }

        binding.habitsList.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            binding.habitsList.context,
            DividerItemDecoration.VERTICAL,
        )
        binding.habitsList.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}