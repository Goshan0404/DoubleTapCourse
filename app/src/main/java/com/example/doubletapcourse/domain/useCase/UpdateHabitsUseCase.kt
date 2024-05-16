package com.example.doubletapcourse.domain.useCase

import com.example.doubletapcourse.domain.HabitRepository
import javax.inject.Inject

class UpdateHabitsUseCase @Inject constructor(private val repository: HabitRepository) {
    suspend operator fun invoke() {
        repository.updateHabits()
    }

}
