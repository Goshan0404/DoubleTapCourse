package com.example.doubletapcourse.presentation.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.doubletapcourse.R
import com.example.doubletapcourse.domain.model.Habit
import com.example.doubletapcourse.domain.model.Interval
import com.example.doubletapcourse.domain.model.Priority
import com.example.doubletapcourse.domain.model.Type
import com.example.doubletapcourse.databinding.FragmentAddHabitBinding
import com.example.doubletapcourse.App
import com.example.doubletapcourse.presentation.viewModel.AddHabitViewModel
import javax.inject.Inject


class AddHabitFragment : Fragment() {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var viewModel: AddHabitViewModel


    companion object {
        const val KEY: String = "key"
        const val HABIT = "habit"
        const val ADD_HABIT = "addHabit"
        const val EDIT_HABIT = "editHabit"

        fun newInstance(key: String, habit: Habit? = null) =
            AddHabitFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(HABIT, habit)
                    putString(KEY, key)
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
    ): View? {
        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable(HABIT, Habit::class.java)?.let { habit ->
            setViewsField(habit)
        }


        saveButtonListener(view)
    }


    private fun setViewsField(habit: Habit) {
        habit.let {
            binding.nameTextView.setText(it.name)
            binding.descriptionTextView.setText(it.description)
            binding.countEditText.setText(it.intervalCount.toString())
            binding.intervalSpinner.setText(it.interval.toString())
            binding.prioritySpinner.setText(it.priority.toString())
            when (it.type) {
                Type.Useful -> binding.typeRadioGroup.check(R.id.useful_radioButton)
                Type.UnUseful -> binding.typeRadioGroup.check(R.id.unuseful_radioButton)
            }

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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun saveButtonListener(view: View) {

        var id = ""
        arguments?.getParcelable(HABIT, Habit::class.java)?.let { habit ->
            id = habit.id!!
        }


        binding.saveButton.setOnClickListener {
            val name = binding.nameTextView.text.toString()
            val description = binding.descriptionTextView.text.toString()
            val type =
                Type.valueOf((view.findViewById<RadioButton>(binding.typeRadioGroup.checkedRadioButtonId)).text.toString())
            val priority = Priority.valueOf(binding.prioritySpinner.text.toString())
            val count = binding.countEditText.text.toString().toInt()
            val interval = Interval.valueOf(binding.intervalSpinner.text.toString())

//          if (name == null || description == null || type == null || priority == null || count == null || interval == null) {
//        }

            viewModel.currentHabit = Habit(id, name, description, type, priority, count, interval, 0, 10)

            viewModel.saveHabit {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
