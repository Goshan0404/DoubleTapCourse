package com.example.doubletapcourse.presentation.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.doubletapcourse.domain.model.HabitDomain
import kotlinx.parcelize.Parcelize


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
) : Parcelable

enum class Interval {
    Week,
    Day,
    Mouth,
    NotChosen;
}

enum class Priority {
    Low,
    High,
    NotChosen;
}

@Parcelize
enum class Type: Parcelable {
    Useful,
    UnUseful,
    NotChosen;
}
