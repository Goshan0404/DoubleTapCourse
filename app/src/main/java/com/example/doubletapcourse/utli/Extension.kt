package com.example.doubletapcourse.utli

import com.example.doubletapcourse.domain.model.HabitDomain
import com.example.doubletapcourse.presentation.model.Habit
import com.example.doubletapcourse.presentation.model.Interval
import com.example.doubletapcourse.presentation.model.Priority
import com.example.doubletapcourse.presentation.model.Type

fun HabitDomain.toLocalHabit(): Habit {
    val currentType = if (type == 0) Type.Useful else Type.UnUseful
    val currentPriority = if (priority == 0) Priority.Low else Priority.High
    val currentInterval = if (interval == 0) Interval.Day else if (interval == 1) Interval.Week else Interval.Mouth
    return Habit(id, name, description, currentType, currentPriority, intervalCount, currentInterval, count, maxCount)
}