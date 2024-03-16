package com.example.doubletapcourse.views.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.HabitStore
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.utlis.ExtraConstants

class AddHabitViewModel : ViewModel() {

    fun saveHabit(key: String, habit: Habit) {
        if (key == ExtraConstants.ADD_HABIT)
            HabitStore.add(habit)
        if (key == ExtraConstants.EDIT_HABIT)
            HabitStore.edit(habit)
    }


}