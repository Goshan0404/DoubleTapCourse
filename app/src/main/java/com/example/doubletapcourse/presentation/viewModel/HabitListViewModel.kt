package com.example.doubletapcourse.presentation.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.data.HabitRepository
import com.example.doubletapcourse.di.component.ActivityScope
import com.example.doubletapcourse.domain.model.Habit
import com.example.doubletapcourse.domain.model.Priority
import com.example.doubletapcourse.domain.model.Type
import com.example.doubletapcourse.presentation.fragments.HabitListFragment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitListViewModel @AssistedInject constructor(
    private val habitRepository: HabitRepository,
    @Assisted handle: SavedStateHandle,
) : ViewModel() {

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

    class SavedStateViewModelFactory @AssistedInject constructor(private val repository: HabitRepository) :
        AbstractSavedStateViewModelFactory() {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return HabitListViewModel(repository, handle) as T
        }
    }
}