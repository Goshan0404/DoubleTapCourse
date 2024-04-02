package com.example.doubletapcourse.presentation.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.data.HabitRepository
import com.example.doubletapcourse.domain.model.Habit
import com.example.doubletapcourse.domain.model.Priority
import com.example.doubletapcourse.domain.model.Type
import com.example.doubletapcourse.presentation.fragments.HabitListFragment
import kotlinx.coroutines.launch

class HabitListViewModel(handle: SavedStateHandle, application: Application) : ViewModel() {

    private val habitRepository = HabitRepository(application)
    private val isPositive: Boolean = handle[HabitListFragment.IS_POSITIVE_HABITS] ?: true
    private val type =
        if (isPositive)
            Type.Useful
        else
            Type.UnUseful
    var currentTypeHabits = MutableLiveData<List<Habit>>()


    init {
        viewModelScope.launch {
            habitRepository.habits.asFlow().collect {
                currentTypeHabits.value = habitRepository.getTypeHabits(type)
            }
        }
    }


    suspend fun getHabits(): List<Habit> {
        viewModelScope.launch {
            currentTypeHabits.value = habitRepository.getTypeHabits(type)
        }.join()

        return currentTypeHabits.value ?: emptyList()
    }

    suspend fun filterHabits(name: String?, priority: String?, filtered: () -> Unit) {
       
            viewModelScope.launch {
                currentTypeHabits.value = habitRepository.getTypeHabits(type)
            }.join()

            name?.let { name ->
                currentTypeHabits.value = currentTypeHabits.value?.filter { it.name == name }
            }
            priority?.let { priority ->
                currentTypeHabits.value =
                    currentTypeHabits.value?.filter { it.priority == Priority.valueOf(priority) }
            }
            filtered()

    }
}