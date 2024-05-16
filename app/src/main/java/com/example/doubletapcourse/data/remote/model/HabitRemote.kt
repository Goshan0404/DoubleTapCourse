package com.example.doubletapcourse.data.remote.model

import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.data.local.model.Interval
import com.example.doubletapcourse.data.local.model.Priority
import com.example.doubletapcourse.data.local.model.Type
import com.example.doubletapcourse.domain.model.HabitDomain
import com.google.gson.annotations.SerializedName

data class HabitRemote(
    val color: Int,
    val count: Int,
    val date: Long,
    val description: String,
    @SerializedName("done_dates")
    val doneDates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String
) {


    fun toHabit(): Habit {
        val currentType = if (type == Type.Useful.ordinal) Type.Useful else Type.UnUseful
        val currentPriority = if (priority == Priority.Low.ordinal) Priority.Low else Priority.High
        val currentInterval =
            if (frequency == Interval.Day.ordinal) Interval.Day else if (frequency == Interval.Week.ordinal) Interval.Week else Interval.Mouth
        return Habit(
            uid,
            title,
            description,
            currentType,
            currentPriority,
            frequency,
            currentInterval,
            count,
            10
        )
    }
}