package com.example.doubletapcourse.data

import com.example.doubletapcourse.App
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Type

object HabitStore {
    private val app = App.getInstance()
    private val habitDao = app.getDb().habitDao()

    val habits = habitDao.getHabits()


    fun save(habit: Habit) {
        habitDao.insert(habit)
    }

    fun getTypeHabits(type: Type): List<Habit> {
        return habitDao.getHabitType(type).value ?: emptyList()
    }
}