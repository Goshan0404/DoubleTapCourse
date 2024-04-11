package com.example.doubletapcourse.domain.useCase

import com.example.doubletapcourse.domain.HabitRepository
import com.example.doubletapcourse.data.local.model.Type
import javax.inject.Inject

class GetHabitsTypeUseCase @Inject constructor(private val habitRepository: HabitRepository) {

    suspend operator fun invoke(type: Int) = habitRepository.getTypeHabits(type)
}