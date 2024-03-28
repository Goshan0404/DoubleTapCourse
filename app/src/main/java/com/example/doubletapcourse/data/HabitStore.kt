package com.example.doubletapcourse.data

import android.app.Application
import com.example.doubletapcourse.App
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Interval
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.data.model.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID

class HabitStore(application: Application) {
    private var app: App = application as App

    private val habitDao = app.getDb().habitDao()


    val habits = habitDao.getHabits()

//    init {
//        val habit = Habit(UUID.randomUUID().toString(), "new", "lala", Type.Useful, Priority.High, 3, Interval.Day)
//
//        CoroutineScope(Dispatchers.Unconfined).launch {
//            habitDao.insert(habit)
//        }
//    }

    suspend fun save(habit: Habit) {
        habitDao.insert(habit)

    }

    suspend fun getTypeHabits(type: Type): List<Habit> {

        return habitDao.getHabitType(type.toString())

    }
}