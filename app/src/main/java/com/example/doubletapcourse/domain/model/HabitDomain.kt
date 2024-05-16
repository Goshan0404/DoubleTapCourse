package com.example.doubletapcourse.domain.model

import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.data.local.model.Interval
import com.example.doubletapcourse.data.local.model.Priority
import com.example.doubletapcourse.data.local.model.Type
import com.example.doubletapcourse.data.remote.model.HabitRemote

data class HabitDomain(
    var id: String,
    var name: String,
    var description: String,
    var type: Int,
    var priority: Int,
    var intervalCount: Int,
    var interval: Int,
    var count: Int,
    var maxCount: Int
) {
    fun toLocalHabit(): Habit {
        val currentType = if (type == 1) Type.Useful else Type.UnUseful
        val currentPriority = if (priority == 0) Priority.Low else Priority.High
        val currentInterval = if (interval == 0) Interval.Day else if (interval == 1) Interval.Week else Interval.Mouth
        return Habit(id, name, description, currentType, currentPriority, intervalCount, currentInterval, count, maxCount)
    }

    fun toRemoteHabit(): HabitRemote {

        return HabitRemote(0, intervalCount, maxCount.toLong()+1, description, listOf(), interval, priority, name, type, id)
    }
}
