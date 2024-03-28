package com.example.doubletapcourse.views.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.doubletapcourse.R
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.databinding.BottomSheetBinding
import com.example.doubletapcourse.databinding.FragmentPagerOfHabitListsBinding
import com.example.doubletapcourse.views.activity.MainActivity
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pagerHabitList.adapter = PagerAdapter(parentFragmentManager)
        binding.tabLayoutHabitsList.setupWithViewPager(binding.pagerHabitList)


        binding.addHabit.setOnClickListener {
            findNavController().navigate(
                R.id.action_pagerOfHabitListsFragment_to_addHabitFragment,
                Bundle().apply { putString(AddHabitFragment.KEY, AddHabitFragment.ADD_HABIT) })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

}
