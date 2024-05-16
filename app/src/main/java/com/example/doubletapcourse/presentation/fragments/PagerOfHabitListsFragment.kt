package com.example.doubletapcourse.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.doubletapcourse.R
import com.example.doubletapcourse.databinding.FragmentPagerOfHabitListsBinding
import com.example.doubletapcourse.data.local.model.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PagerOfHabitListsFragment : Fragment() {
    private var _binding: FragmentPagerOfHabitListsBinding? = null
    private val binding get() = _binding!!

    private val bottomSheetFilterFragment = BottomSheetFilterFragment()

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

        binding.pagerHabitList.adapter = PagerAdapter(childFragmentManager)
        binding.tabLayoutHabitsList.setupWithViewPager(binding.pagerHabitList)


        binding.addHabit.setOnClickListener {
            findNavController().navigate(
                R.id.action_pagerOfHabitListsFragment_to_addHabitFragment,
            )
        }

        binding.filterButton.setOnClickListener {
            bottomSheetFilterFragment.show(childFragmentManager, BottomSheetFilterFragment.TAG)
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
                0 -> HabitListFragment.newInstance(Type.Useful)
                else -> HabitListFragment.newInstance(Type.UnUseful)
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
