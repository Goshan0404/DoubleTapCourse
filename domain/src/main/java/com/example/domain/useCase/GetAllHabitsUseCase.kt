package com.example.doubletapcourse.domain.useCase

import com.example.doubletapcourse.domain.HabitRepository
import javax.inject.Inject

class GetAllHabitsUseCase @Inject constructor(private val repository: HabitRepository) {
    operator fun invoke() = repository.habits
}