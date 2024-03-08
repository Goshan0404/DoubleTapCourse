package com.example.doubletapcourse.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Habit(
    val id: String,
    val name: String,
    val description: String,
    val type: Type,
    val priority: Priority,
    val count: Int,
    val interval: Interval
): Parcelable

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
