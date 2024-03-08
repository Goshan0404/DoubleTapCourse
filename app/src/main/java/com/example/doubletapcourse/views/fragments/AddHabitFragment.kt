package com.example.doubletapcourse.views.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.doubletapcourse.R
import com.example.doubletapcourse.utlis.ExtraConstants
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Interval
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.databinding.FragmentAddHabitBinding
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.views.viewModel.AddHabitViewModel
import java.util.UUID


class AddHabitFragment : Fragment() {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddHabitViewModel by viewModels()


    companion object {

        fun newInstance(key: String, habit: Habit? = null) =
            AddHabitFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ExtraConstants.HABIT, habit)
                    putString(ExtraConstants.KEY, key)
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

        val key = arguments?.getString(ExtraConstants.KEY)

        arguments?.getParcelable(ExtraConstants.HABIT, Habit::class.java)?.let { habit ->
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

            val intervalAdapter = ArrayAdapter<String>(
                requireActivity(), android.R.layout.simple_dropdown_item_1line, requireActivity().resources.getStringArray(
                    R.array.intervals_array)
            )
            binding.intervalSpinner.setAdapter(intervalAdapter)

            val priorityAdapter = ArrayAdapter<String>(
                requireActivity(), android.R.layout.simple_dropdown_item_1line, requireActivity().resources.getStringArray(
                    R.array.priority_array)
            )
            binding.prioritySpinner.setAdapter(priorityAdapter)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun saveButtonListener(view: View, key: String) {
        binding.saveButton.setOnClickListener {

            val isEmptyFields =
                binding.typeRadioGroup.checkedRadioButtonId == -1 ||
                        isEmptyFields(
                            binding.nameTextView.text.toString(),
                            binding.descriptionTextView.text.toString(),
                            (view.findViewById<RadioButton>(binding.typeRadioGroup.checkedRadioButtonId)).text.toString(),
                            binding.prioritySpinner.text.toString(),
                            binding.countEditText.text.toString(),
                            binding.intervalSpinner.text.toString()
                        )
            if (isEmptyFields) {
                Toast.makeText(view.context, "fields must by not empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val habit = getHabit(view)
            viewModel.saveHabit(key, habit)

            parentFragmentManager.setFragmentResult(key, Bundle())

            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getHabit(view: View): Habit {
        val id = arguments?.getParcelable(ExtraConstants.HABIT, Habit::class.java)?.id


        return Habit(
            id ?: UUID.randomUUID().toString(),
            binding.nameTextView.text.toString(),
            binding.descriptionTextView.text.toString(),
            Type.valueOf(view.findViewById<RadioButton>(binding.typeRadioGroup.checkedRadioButtonId).text.toString()),
            Priority.valueOf(binding.prioritySpinner.text.toString()),
            binding.countEditText.text.toString().toInt(),
            Interval.valueOf(binding.intervalSpinner.text.toString())
        )

    }

    private fun isEmptyFields(vararg strings: String): Boolean {
        for (string in strings) {
            if (string.isEmpty())
                return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
