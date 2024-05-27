package com.example.doubletapcourse.presentation.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.domain.useCase.DoneHabitUseCase
import com.example.doubletapcourse.App
import com.example.doubletapcourse.R
import com.example.doubletapcourse.databinding.FragmentHabitListBinding
import com.example.doubletapcourse.di.factory.HabitListViewModelFactory
import com.example.doubletapcourse.presentation.adapter.HabitAdapter
import com.example.doubletapcourse.presentation.model.Type
import com.example.doubletapcourse.presentation.viewModel.HabitListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class HabitListFragment : Fragment() {

    companion object {
        const val IS_POSITIVE_HABITS = "isPositiveHabits"

        @JvmStatic
        fun newInstance(type: Type) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IS_POSITIVE_HABITS, type)
                }
            }
    }

    private var _binding: FragmentHabitListBinding? = null

    private val binding get() = _binding!!
    private val viewModel: HabitListViewModel by activityViewModels {
        factory.create()
    }

    @Inject
    lateinit var factory: HabitListViewModelFactory


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val listAdapter: HabitAdapter = HabitAdapter(
        itemClick = {
            findNavController().navigate(
                R.id.action_pagerOfHabitListsFragment_to_addHabitFragment,
                Bundle().apply {
                    putString(AddHabitFragment.HABIT_ID, it.id)
                }
            )
        },
        doneClick = {
            viewModel.doneButtonClicked(it, arguments?.getParcelable(IS_POSITIVE_HABITS, Type::class.java)!!)
        })


    override fun onAttach(context: Context) {
        (requireActivity().application as App).applicationComponent.habitComponent().create()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(IS_POSITIVE_HABITS, Type::class.java)!!
        } else {
            arguments?.getParcelable(IS_POSITIVE_HABITS)!!
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getHabits(type)
                .collect {
                    listAdapter.setData(it)
                }
        }

        lifecycleScope.launch {
            doneButtonState(type)
        }
    }

    private suspend fun doneButtonState(type: Type) {
        viewModel.getState(type).collect() {
            when (it) {
                is DoneHabitUseCase.DoneState.PositiveHabitDoneLess ->
                    Toast.makeText(
                        context,
                        getString(R.string.may_do_more, it.count.toString()),
                        Toast.LENGTH_SHORT
                    ).show()

                is DoneHabitUseCase.DoneState.NegativeHabitDoneLess ->
                    Toast.makeText(
                        context,
                        getString(R.string.have_do_more, it.count.toString()),
                        Toast.LENGTH_SHORT
                    ).show()

                is DoneHabitUseCase.DoneState.NegativeHabitDoneOverFlow ->
                    Toast.makeText(context,
                        getString(R.string.stop_do), Toast.LENGTH_SHORT)
                        .show()

                is DoneHabitUseCase.DoneState.PositiveHabitDoneOverFlow ->
                    Toast.makeText(
                        context,
                        getString(R.string.you_are_breathtaking),
                        Toast.LENGTH_SHORT
                    ).show()

                else -> {}
            }
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

    sealed class HabitListFragmentState {
        data object StopDo : HabitListFragmentState()
        class MustDo(val count: Int) : HabitListFragmentState()
        class MayDo(val count: Int) : HabitListFragmentState()
        data object Overfulfilled : HabitListFragmentState()
        data object NoState : HabitListFragmentState()

    }
}