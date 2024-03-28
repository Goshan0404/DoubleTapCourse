package com.example.doubletapcourse.views.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.doubletapcourse.R
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.databinding.BottomSheetBinding
import com.example.doubletapcourse.databinding.FragmentHabitListBinding
import com.example.doubletapcourse.views.activity.MainActivity
import com.example.doubletapcourse.views.adapter.HabitAdapter
import com.example.doubletapcourse.views.viewModel.HabitListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class HabitListFragment : Fragment() {


    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitListViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory() {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return HabitListViewModel(handle, requireActivity().application) as T
            }

        }
    }


    private var habits: List<Habit> = arrayListOf()

    private val listAdapter: HabitAdapter = HabitAdapter(habits) { habit: Habit, position: Int ->

        findNavController().navigate(
            R.id.action_pagerOfHabitListsFragment_to_addHabitFragment,
            Bundle().apply {
                putString(AddHabitFragment.KEY, AddHabitFragment.EDIT_HABIT)
                putParcelable(AddHabitFragment.HABIT, habit)
            }
        )
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

        lifecycleScope.launch {
            launch {
                habits = viewModel.getHabits()
            }.join()

            setAdapter()
        }

        viewModel.currentTypeHabits.observe(requireActivity()) {
            listAdapter.setData(it)
        }
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

    class BottomSheetFilter : BottomSheetDialogFragment() {
        private var _binding: BottomSheetBinding? = null
        private val binding get() = _binding!!
//        private val viewModel: HabitListViewModel = viewmodel

        companion object {
            const val TAG = "BottomSheetFilter"
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = BottomSheetBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

//            binding.filterButton.setOnClickListener {
//                viewModel.currentTypeHabits.value =
//                    viewModel.currentTypeHabits.value?.filter {
//                        it.name == binding.nameSearchTextView.text.toString()
//                    }
//                        ?.filter { it.priority == Priority.valueOf(binding.prioritySearchSpinner.text.toString()) }
//
//            }
        }


    }
}