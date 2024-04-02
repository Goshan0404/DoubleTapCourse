package com.example.doubletapcourse.data.remote.model

import com.example.doubletapcourse.domain.model.Habit
import com.example.doubletapcourse.domain.model.Interval
import com.example.doubletapcourse.domain.model.Priority
import com.example.doubletapcourse.domain.model.Type
import com.google.gson.annotations.SerializedName

data class HabitRemote(
    val color: Int,
    val count: Int,
    val date: Long,
    val description: String,
    val done_dates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String
) {
    fun toHabit(): Habit {
        val currentType = if (type == 1) Type.Useful else Type.UnUseful
        val currentPriority = if (priority == 0) Priority.Low else Priority.High
        val currentInterval = if (frequency == 0) Interval.Day else if (frequency == 1) Interval.Week else Interval.Mouth
        return Habit(uid, title, description, currentType, currentPriority, count, currentInterval)
    }
}