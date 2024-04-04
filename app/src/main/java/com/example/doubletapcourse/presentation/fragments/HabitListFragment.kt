package com.example.doubletapcourse.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.doubletapcourse.R
import com.example.doubletapcourse.domain.model.Habit
import com.example.doubletapcourse.databinding.BottomSheetBinding
import com.example.doubletapcourse.databinding.FragmentHabitListBinding
import com.example.doubletapcourse.App
import com.example.doubletapcourse.di.factory.ViewModelFactory
import com.example.doubletapcourse.domain.model.Type
import com.example.doubletapcourse.presentation.adapter.HabitAdapter
import com.example.doubletapcourse.presentation.viewModel.HabitListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class HabitListFragment : Fragment() {


    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitListViewModel by viewModels {
        factory.create()
    }
    @Inject lateinit var factory: ViewModelFactory

    private val bottomSheetFilter = BottomSheetFilter()


    private var habits: List<Habit> = arrayListOf()

    private val listAdapter: HabitAdapter = HabitAdapter(habits,
        {
            findNavController().navigate(
                R.id.action_pagerOfHabitListsFragment_to_addHabitFragment,
                Bundle().apply {
                    putString(AddHabitFragment.KEY, AddHabitFragment.EDIT_HABIT)
                    putParcelable(AddHabitFragment.HABIT, it)
                }
            )
        }, {
            doneButtonClicked(it)
        })

    private fun doneButtonClicked(it: Habit) {
        it.count++
        if (it.type == Type.UnUseful)
            if (it.count < it.maxCount)
                Toast.makeText(
                    requireActivity(),
                    "Можно выполнить еще ${it.maxCount - it.count} раз",
                    Toast.LENGTH_SHORT
                ).show()
            else
                Toast.makeText(requireActivity(), "Хватит это делать", Toast.LENGTH_SHORT)
        else
            if (it.count < it.maxCount)
                Toast.makeText(
                    requireActivity(),
                    "Нужно выполнить еще ${it.maxCount - it.count}",
                    Toast.LENGTH_SHORT
                ).show()
            else
                Toast.makeText(
                    requireActivity(),
                    "You are breathtaking!",
                    Toast.LENGTH_SHORT
                ).show()
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


    override fun onAttach(context: Context) {
        (requireActivity().application as App).applicationComponent.habitComponent().create().inject(this)
        super.onAttach(context)
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

        binding.filterButton.setOnClickListener {
            bottomSheetFilter.show(childFragmentManager, BottomSheetFilter.TAG)

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
        private val viewModel: HabitListViewModel by viewModels(ownerProducer = { requireParentFragment() })

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

            binding.filterButton.setOnClickListener {

                val name = if (!binding.nameSearchTextView.text.toString().isEmpty()) {
                    binding.nameSearchTextView.text.toString()
                } else
                    null

                val priority = if (!binding.prioritySearchSpinner.text.toString().isEmpty())
                    binding.prioritySearchSpinner.text.toString()
                else
                    null

                lifecycleScope.launch {

                    viewModel.filterHabits(name, priority) {
                        this@BottomSheetFilter.dismiss()
                    }
                }
            }
        }


    }
}