package com.example.doubletapcourse.presentation.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.data.HabitRepository
import com.example.doubletapcourse.di.component.ActivityScope
import com.example.doubletapcourse.domain.model.Habit
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class AddHabitViewModel @Inject constructor(private val habitRepository: HabitRepository) :
    ViewModel() {


    lateinit var currentHabit: Habit

    fun saveHabit(success: () -> Unit) {

        viewModelScope.launch {
            launch {
                habitRepository.save(currentHabit)
            }.join()
            success()
        }

    }

}