package com.example.doubletapcourse.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.doubletapcourse.data.remote.model.HabitRemote
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
@Entity
data class Habit(
    @PrimaryKey var id: String,
    val name: String,
    val description: String,
    val type: Type,
    val priority: Priority,
    val intervalCount: Int,
    val interval: Interval,
    var count: Int,
    val maxCount: Int
) : Parcelable {
    fun toHabitRemote(): HabitRemote {
        val priorityInt = if (priority == Priority.Low) 0 else 1
        val typeInt = if (type == Type.Useful) 1 else 0
        val intervalInt = if (interval == Interval.Day) 0 else if(interval == Interval.Week) 1 else 2
        return HabitRemote(0, intervalCount,
            Date().time, description, listOf(10), intervalInt, priorityInt, name, typeInt, id!!)
    }
}

enum class Interval {
    Week,
    Day,
    Mouth
}

enum class Priority {
    High,
    Low
}

enum class Type {
    Useful,
    UnUseful
}
