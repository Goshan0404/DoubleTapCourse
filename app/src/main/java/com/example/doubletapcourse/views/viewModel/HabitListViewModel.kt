package com.example.doubletapcourse.views.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.data.HabitStore
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.views.fragments.HabitListFragment
import kotlinx.coroutines.launch

class HabitListViewModel(handle: SavedStateHandle, application: Application) : ViewModel() {

    private val habitStore = HabitStore(application)
    private val isPositive: Boolean = handle[HabitListFragment.IS_POSITIVE_HABITS] ?: true
    private val type =
        if (isPositive)
            Type.Useful
        else
            Type.UnUseful
    var currentTypeHabits = MutableLiveData<List<Habit>>()


    init {
        viewModelScope.launch {
            habitStore.habits.asFlow().collect {
                currentTypeHabits.value = habitStore.getTypeHabits(type)
            }
        }
    }


    suspend fun getHabits(): List<Habit> {
        viewModelScope.launch {
            currentTypeHabits.value = habitStore.getTypeHabits(type)
        }.join()

        return currentTypeHabits.value ?: emptyList()
    }
}