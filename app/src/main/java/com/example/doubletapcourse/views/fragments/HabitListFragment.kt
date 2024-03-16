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
import com.example.doubletapcourse.data.model.Interval
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.databinding.FragmentHabitListBinding
import com.example.doubletapcourse.utlis.ExtraConstants
import com.example.doubletapcourse.views.adapter.HabitAdapter
import com.example.doubletapcourse.views.viewModel.HabitListViewModel
import java.util.UUID


class HabitListFragment : Fragment() {
    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!


    private lateinit var habits: List<Habit>

    private val adapter: HabitAdapter = HabitAdapter(habits) { habit: Habit, position: Int ->

        parentFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                AddHabitFragment.newInstance(ExtraConstants.EDIT_HABIT, habit)
            ).hide(parentFragmentManager.findFragmentById(R.id.fragment_container)!!).commit()

    }


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habit = Habit(UUID.randomUUID().toString(), "new", "lala", Type.Useful, Priority.High, 3, Interval.Day)
        habits = arrayListOf(habit)

        binding.habitsList.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            binding.habitsList.context,
            DividerItemDecoration.VERTICAL,
        )
        binding.habitsList.addItemDecoration(dividerItemDecoration)

        setFragmentResultListener(ExtraConstants.EDIT_HABIT)
        setFragmentResultListener(ExtraConstants.ADD_HABIT)
    }

    private fun setFragmentResultListener(key: String) {
        parentFragmentManager.setFragmentResultListener(
            key,
            this
        ) { _: String, _: Bundle ->
//            habits = viewModel.getHabits()
            adapter.notifyDataSetChanged()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}