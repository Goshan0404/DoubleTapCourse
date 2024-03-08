package com.example.doubletapcourse.data

import androidx.lifecycle.MutableLiveData
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Interval
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.data.model.Type
import java.util.UUID

object HabitStore {
    val habits = MutableLiveData<MutableList<Habit>>().apply { value = mutableListOf() }
     var positiveHabits = MutableLiveData<MutableList<Habit>>().apply { value = mutableListOf() }
     val negativeHabits = MutableLiveData<MutableList<Habit>>().apply { value = mutableListOf() }

    init {
        val habit = Habit(UUID.randomUUID().toString(), "new", "lala", Type.Useful, Priority.High, 3, Interval.Day)

        habits.value?.add(habit)
        positiveHabits.value?.add(habit)
    }

}