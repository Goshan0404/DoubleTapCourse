package com.example.doubletapcourse.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Interval
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.data.model.Type
import java.util.UUID

object HabitStore {

    private val _habits = MutableLiveData<MutableList<Habit>>().apply { value = mutableListOf() }
    val habits: LiveData<MutableList<Habit>> = _habits

    init {
        val habit = Habit(UUID.randomUUID().toString(), "new", "lala", Type.Useful, Priority.High, 3, Interval.Day)

        _habits.value = mutableListOf()
//        _habits.value?.add(habit)
    }

    fun add(habit: Habit) {
        _habits.value?.add(habit)
        _habits.value = _habits.value
    }
    fun edit(newHabit: Habit) {
        val indexOfHabit = _habits.value?.indexOfFirst { newHabit.id == it.id }!!
        _habits.value?.let {
            it[indexOfHabit] = newHabit
        }
        _habits.value = _habits.value
    }

    fun getTypeHabits(type: Type): List<Habit> {
        return _habits.value?.filter { it.type == type } ?: emptyList()
    }
}