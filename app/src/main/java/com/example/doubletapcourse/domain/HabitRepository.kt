package com.example.doubletapcourse.domain

import androidx.lifecycle.LiveData
import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.data.local.model.Type
import com.example.doubletapcourse.domain.model.HabitDomain
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    val habits: Flow<List<HabitDomain>>
    suspend fun save(habit: HabitDomain)
    suspend fun getTypeHabits(type: Int): List<HabitDomain>

    suspend fun getHabitById(id: String): Flow<HabitDomain?>
}