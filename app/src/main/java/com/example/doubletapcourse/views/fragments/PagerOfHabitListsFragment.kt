package com.example.doubletapcourse.views.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.example.doubletapcourse.R
import com.example.doubletapcourse.databinding.BottomSheetBinding
import com.example.doubletapcourse.databinding.FragmentPagerOfHabitListsBinding
import com.example.doubletapcourse.utlis.ExtraConstants
import com.example.doubletapcourse.views.viewModel.HabitListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PagerOfHabitListsFragment : Fragment() {
    private var _binding: FragmentPagerOfHabitListsBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() =
            PagerOfHabitListsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPagerOfHabitListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pagerHabitList.adapter = PagerAdapter(parentFragmentManager)
        binding.tabLayoutHabitsList.setupWithViewPager(binding.pagerHabitList)

        setFragmentResultListener(ExtraConstants.ADD_HABIT)
        setFragmentResultListener(ExtraConstants.EDIT_HABIT)

        binding.addHabit.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    AddHabitFragment.newInstance(ExtraConstants.ADD_HABIT)
                )
                .hide(this)
                .commit()
        }

        val bottomSheet = BottomSheetFilter()
        binding.filterButton.setOnClickListener {
            bottomSheet.show(parentFragmentManager, BottomSheetFilter.TAG)
        }

    }

    private fun setFragmentResultListener(key: String) {
        parentFragmentManager.setFragmentResultListener(
            key,
            this
        ) { _: String, _: Bundle ->
            parentFragmentManager.beginTransaction().show(this).commit()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HabitListFragment.newInstance(true)
            else -> HabitListFragment.newInstance(false)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0)
            "Positive Habits"
        else
            "Negative Habits"
    }
}

class BottomSheetFilter : BottomSheetDialogFragment() {
    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitListViewModel by viewModels()

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
        }
    }


}