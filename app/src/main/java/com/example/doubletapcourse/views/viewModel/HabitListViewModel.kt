package com.example.doubletapcourse.views.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.HabitStore
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.utlis.ExtraConstants
import com.example.doubletapcourse.views.fragments.HabitListFragment
import kotlinx.coroutines.launch

class HabitListViewModel(handle: SavedStateHandle) : ViewModel() {
    private val isPositive: Boolean = handle[HabitListFragment.IS_POSITIVE_HABITS] ?: true
    private val type =
        if (isPositive)
            Type.Useful
        else
            Type.UnUseful

    var currentTypeHabits = MutableLiveData<List<Habit>>()
        .apply {
            value = HabitStore.getTypeHabits(type)
        }

    init {
        viewModelScope.launch {
            HabitStore.habits.asFlow().collect {
                currentTypeHabits.value = it
            }
        }
    }

//    fun getHabits() {
//        currentTypeHabits.value = HabitStore.getTypeHabits(type)
//    }
}