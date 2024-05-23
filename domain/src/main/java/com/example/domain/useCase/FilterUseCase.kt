package com.example.doubletapcourse.domain.useCase

import com.example.doubletapcourse.domain.model.HabitDomain
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FilterUseCase @Inject constructor() {
    operator fun invoke(
        nameFilter: SharedFlow<String>,
        priorityFilter: SharedFlow<Int>,
        currentTypeHabits: SharedFlow<List<HabitDomain>>,
    ) {
        currentTypeHabits.combine(nameFilter) {
            habits, name  ->
            habits.filter { it.name == name }
        }

        currentTypeHabits.combine(priorityFilter) {
                habits, priority  ->
            habits.filter { it.priority == priority }
        }
    }
}