package com.example.doubletapcourse.domain

import com.example.doubletapcourse.domain.model.HabitDomain
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    val habits: Flow<List<HabitDomain>>
    suspend fun save(habit: HabitDomain): Boolean
    suspend fun getTypeHabits(type: Int): List<HabitDomain>

    suspend fun updateHabits(): Boolean
    fun getHabitById(id: String): Flow<HabitDomain?>
}