package com.example.doubletapcourse.domain.useCase

import com.example.doubletapcourse.domain.HabitRepository
import com.example.doubletapcourse.domain.model.HabitDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitByIdUseCase @Inject constructor(private val habitRepository: HabitRepository) {

    operator fun invoke(id: String): Flow<HabitDomain?> {

        return habitRepository.getHabitById(id)
    }
}
