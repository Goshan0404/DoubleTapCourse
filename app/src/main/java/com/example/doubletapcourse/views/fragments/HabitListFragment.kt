package com.example.doubletapcourse.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.doubletapcourse.R
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.databinding.FragmentHabitListBinding
import com.example.doubletapcourse.views.adapter.HabitAdapter
import com.example.doubletapcourse.views.viewModel.HabitListViewModel


class HabitListFragment : Fragment() {


    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitListViewModel by viewModels()

    private var habits: List<Habit> =  arrayListOf()

    private val listAdapter: HabitAdapter = HabitAdapter(habits) { habit: Habit, position: Int ->

        parentFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                AddHabitFragment.newInstance(AddHabitFragment.EDIT_HABIT, habit)
            ).hide(parentFragmentManager.findFragmentById(R.id.fragment_container)!!).commit()
    }


    companion object {

        const val IS_POSITIVE_HABITS = "isPositiveHabits"

        @JvmStatic
        fun newInstance(isPositiveHabits: Boolean) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_POSITIVE_HABITS, isPositiveHabits)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        habits = viewModel.currentTypeHabits.value!!

        viewModel.currentTypeHabits.observe(requireActivity()) {
            listAdapter.setData(it)
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.habitsList.adapter = listAdapter
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