package com.example.doubletapcourse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Habit(
    val name: String,
    val description: String,
    val type: String,
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
