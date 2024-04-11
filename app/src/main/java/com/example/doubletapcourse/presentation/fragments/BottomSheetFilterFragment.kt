package com.example.doubletapcourse.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.doubletapcourse.databinding.BottomSheetBinding
import com.example.doubletapcourse.presentation.viewModel.HabitListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class BottomSheetFilterFragment : BottomSheetDialogFragment() {
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

        binding.nameSearchTextView.doOnTextChanged { text, _, _, _ ->
            viewModel.nameSearchChanged(text)
        }

        binding.prioritySearchSpinner.doOnTextChanged { text, _, _, _ ->
            viewModel.priorityChanged(text)
        }

        binding.filterButton.setOnClickListener {

            viewModel.filterHabits()

            this.dismiss()

        }
    }


}