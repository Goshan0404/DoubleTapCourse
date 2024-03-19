package com.example.doubletapcourse.views.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.HabitStore
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.utlis.ExtraConstants

class HabitListViewModel(handle: SavedStateHandle) : ViewModel() {
    private val isPositive: Boolean = handle[ExtraConstants.IS_POSITIVE_HABITS] ?: true
    private val type = if (isPositive) Type.Useful else Type.UnUseful

    fun getHabits(): List<Habit> {
        return HabitStore.getTypeHabits(type)
    }
}