package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.doubletapcourse.domain.model.HabitDomain

@Entity
data class HabitData(
    val count: Int,
    val date: Long,
    val description: String,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    @PrimaryKey
    val uid: String
) {

    fun toHabitDomain(): HabitDomain {
        return HabitDomain(uid, title, description, type, priority, frequency%10, frequency/10, count, 10)
    }
}
