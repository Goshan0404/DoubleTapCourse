package com.example.doubletapcourse.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.doubletapcourse.data.remote.model.HabitRemote
import com.example.doubletapcourse.domain.model.HabitDomain
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
@Entity
data class Habit(
    @PrimaryKey var id: String,
    var name: String,
    var description: String,
    var type: Type,
    var priority: Priority,
    var intervalCount: Int,
    var interval: Interval,
    var count: Int,
    var maxCount: Int
) : Parcelable {

    fun toHabitRemote(): HabitRemote {
        return HabitRemote(0, intervalCount,
            Date().time, description, listOf(10), interval.toInt(), priority.toInt(), name, type.toInt(), id
        )
    }

    fun toHabitDomain(): HabitDomain {
        return HabitDomain(id, name, description, type.toInt(), priority.toInt(), intervalCount, interval.toInt(), count, maxCount)
    }
}

enum class Interval {
    Week,
    Day,
    Mouth,
    NotChosen;

    fun toInt(): Int {
        return if (this == Day) 0 else if (this == Week) 1 else 2
    }
}

enum class Priority {
    High,
    Low,
    NotChosen;

    fun toInt(): Int {
        return if (this == Low) 0 else 1
    }
}

@Parcelize
enum class Type: Parcelable {
    Useful,
    UnUseful,
    NotChosen;

    fun toInt(): Int {
        return if (this == Useful) 1 else 0
    }
}
