package com.example.doubletapcourse.views.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.HabitStore

class HabitListViewModel : ViewModel() {

    private val habits = HabitStore.habits
    private var _positiveHabits = HabitStore.positiveHabits
    private val _negativeHabits = HabitStore.negativeHabits

    val positiveHabits: LiveData<MutableList<Habit>> = _positiveHabits
    val negativeHabits: LiveData<MutableList<Habit>> = _negativeHabits


}