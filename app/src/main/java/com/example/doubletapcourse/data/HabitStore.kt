package com.example.doubletapcourse.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.doubletapcourse.App
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Interval
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.data.model.Type
import java.util.UUID

object HabitStore {
    private val app = App.getInstance()
    private val habitDao = app.getDb().habitDao()

    val habits = habitDao.getHabits()


    fun save(habit: Habit) {
        habitDao.insert(habit)
    }

//    fun add(habit: Habit) {
//        habitDao.insert(habit)
//    }
//    fun edit(newHabit: Habit) {
//        val indexOfHabit = _habits.value?.indexOfFirst { newHabit.id == it.id }!!
//        _habits.value?.let {
//            it[indexOfHabit] = newHabit
//        }
//        _habits.value = _habits.value
//    }

    fun getTypeHabits(type: Type): List<Habit> {
        return habitDao.getHabitType(type).value ?: emptyList()
    }
}