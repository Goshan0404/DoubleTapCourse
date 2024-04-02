package com.example.doubletapcourse.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.data.HabitRepository
import com.example.doubletapcourse.domain.model.Habit
import kotlinx.coroutines.launch

class AddHabitViewModel(application: Application) : ViewModel() {
    private val habitRepository = HabitRepository(application)
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