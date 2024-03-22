package com.example.doubletapcourse.views.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.doubletapcourse.R
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.databinding.FragmentAddHabitBinding
import com.example.doubletapcourse.views.viewModel.AddHabitViewModel


class AddHabitFragment : Fragment() {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddHabitViewModel by viewModels()


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

        val key = arguments?.getString(KEY)

        arguments?.getParcelable(HABIT, Habit::class.java)?.let { habit ->
            viewModel.id = habit.id
            setViewsField(habit)
        }


        saveButtonListener(view, key!!)
    }


    private fun setViewsField(habit: Habit) {
        habit.let {
            binding.nameTextView.setText(it.name)
            binding.descriptionTextView.setText(it.description)
            binding.countEditText.setText(it.count.toString())
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
    private fun saveButtonListener(view: View, key: String) {
        binding.saveButton.setOnClickListener {
            viewModel.interval = binding.intervalSpinner.text.toString()
            viewModel.count = binding.countEditText.text.toString()
            viewModel.description = binding.descriptionTextView.text.toString()
            viewModel.name = binding.nameTextView.text.toString()
            viewModel.priority = binding.prioritySpinner.text.toString()
            viewModel.type = (view.findViewById<RadioButton>(binding.typeRadioGroup.checkedRadioButtonId)).text.toString()

            viewModel.saveHabit(key, {
                parentFragmentManager.setFragmentResult(key, Bundle())

                parentFragmentManager.beginTransaction()
                    .remove(this)
                    .commit()
            }, {
                Toast.makeText(view.context, "fields must by not empty", Toast.LENGTH_SHORT).show()
            })


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
