package com.example.doubletapcourse.presentation.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.doubletapcourse.App
import com.example.doubletapcourse.R
import com.example.doubletapcourse.databinding.FragmentAddHabitBinding
import com.example.doubletapcourse.di.factory.AddHabitViewModelFactory
import com.example.doubletapcourse.presentation.model.Habit
import com.example.doubletapcourse.presentation.model.Type
import com.example.doubletapcourse.presentation.viewModel.AddHabitViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddHabitFragment : Fragment() {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!


    private val viewModel: AddHabitViewModel by viewModels {
        factory.create()
    }

    @Inject
    lateinit var factory: AddHabitViewModelFactory


    companion object {
        const val HABIT_ID = "habit"

        fun newInstance(key: String, habit: Habit? = null) =
            AddHabitFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as App).applicationComponent.habitComponent().create()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(HABIT_ID, Habit::class.java)?.let { habit ->
                setViewsField(habit)
            }
        } else {
            arguments?.getParcelable<Habit>(HABIT_ID)?.let { habit ->
                setViewsField(habit)
            }
        }

        setViewListeners()

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is AddHabitFragmentState.NavigateUp -> findNavController().navigateUp()
                    is AddHabitFragmentState.HabitExist -> setViewsField(it.habit)
                    is AddHabitFragmentState.EmptyFields -> toastOnEmptyField()
                    is AddHabitFragmentState.Error -> toastErrorRequest()
                    is AddHabitFragmentState.NoState -> {}
                }
            }

        }
    }


    private fun setViewsField(habit: Habit) {
        habit.let {
            binding.habitNameTextView.setText(it.name)
            binding.habitDescriptionTextView.setText(it.description)
            binding.countEditText.setText(it.intervalCount.toString())
            binding.intervalSpinner.setText(it.interval.toString())
            binding.prioritySpinner.setText(it.priority.toString())

            if (it.type == Type.Useful)
                binding.typeRadioGroup.check(R.id.useful_radioButton)

            if (it.type == Type.UnUseful)
                binding.typeRadioGroup.check(R.id.unuseful_radioButton)


            val intervalAdapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_dropdown_item_1line,
                requireActivity().resources.getStringArray(
                    R.array.intervals_array
                )
            )
            binding.intervalSpinner.setAdapter(intervalAdapter)

            val priorityAdapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_dropdown_item_1line,
                requireActivity().resources.getStringArray(
                    R.array.priority_array
                )
            )
            binding.prioritySpinner.setAdapter(priorityAdapter)
        }
    }

    private fun setViewListeners() {

        binding.countEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.countChanged(text)
        }

        binding.habitNameTextView.doOnTextChanged { text, _, _, _ ->
            viewModel.nameChanged(text)
        }

        binding.typeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.typeChanged(checkedId)
        }

        binding.habitDescriptionTextView.doOnTextChanged { text, _, _, _ ->
            viewModel.descriptionChanged(text)
        }

        binding.prioritySpinner.doOnTextChanged { text, _, _, _ ->
            viewModel.priorityChanged(text)
        }

        binding.intervalSpinner.doOnTextChanged { text, _, _, _ ->
            viewModel.intervalChanged(text)
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveHabit()
        }
    }

    private fun toastOnEmptyField() {
        Toast.makeText(context, "Empty Fields", Toast.LENGTH_SHORT).show()
    }

    private fun toastErrorRequest() {
        Toast.makeText(context, "Error request", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    sealed class AddHabitFragmentState {
        data object NoState : AddHabitFragmentState()
        data object NavigateUp : AddHabitFragmentState()
        data class HabitExist(val habit: Habit) : AddHabitFragmentState()
        data object Error: AddHabitFragmentState()
        data object EmptyFields : AddHabitFragmentState()
    }

}
