package com.example.doubletapcourse.domain.useCase

import com.example.doubletapcourse.domain.HabitRepository
import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.domain.model.HabitDomain
import javax.inject.Inject

class SaveHabitUseCase @Inject constructor(private val habitRepository: HabitRepository) {
    suspend operator fun invoke(habit: HabitDomain) {
        habitRepository.save(habit)
    }
}