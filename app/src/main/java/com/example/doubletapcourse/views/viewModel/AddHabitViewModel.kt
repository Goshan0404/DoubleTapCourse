package com.example.doubletapcourse.views.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.HabitStore
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.utlis.ExtraConstants

class AddHabitViewModel : ViewModel() {
    private val habits = HabitStore.habits
    private var positiveHabits = HabitStore.positiveHabits
    private val negativeHabits = HabitStore.negativeHabits


    fun saveHabit(key: String, habit: Habit) {
        if (key == ExtraConstants.ADD_HABIT)
            add(habit)
        if (key == ExtraConstants.EDIT_HABIT)
            edit(habit)
    }

    private fun add(habit: Habit) {
        habits.value?.add(habit)
        if (habit.type == Type.Useful) {
            add(positiveHabits, habit)
        }

        if (habit.type == Type.UnUseful) {
            add(negativeHabits, habit)
        }
    }

    private fun edit(newHabit: Habit) {
        val lastHabit = habits.value?.firstOrNull { newHabit.id == it.id }!!

        if (lastHabit.type != newHabit.type)
            if (negativeHabits.value?.removeIf { it.id == newHabit.id } == true) {
                add(positiveHabits, newHabit)
                negativeHabits.value = negativeHabits.value
            } else if (positiveHabits.value?.removeIf { it.id == newHabit.id } == true) {
               add(negativeHabits, newHabit)
                positiveHabits.value = positiveHabits.value
            }

        if (lastHabit.type == newHabit.type)

            if (newHabit.type == Type.Useful) {
                edit(positiveHabits, newHabit)
            } else if (newHabit.type == Type.UnUseful) {
                edit(negativeHabits, newHabit)
            }
    }

    private fun add(habits: MutableLiveData<MutableList<Habit>>, newHabit: Habit) {
        habits.value?.add(newHabit)
        habits.value = habits.value
    }

    private fun edit(habits: MutableLiveData<MutableList<Habit>>, newHabit: Habit) {
        val index = habits.value?.indexOfFirst { newHabit.id == it.id }!!
        habits.value!![index] = newHabit
        habits.value = habits.value
    }
}