package com.example.doubletapcourse.domain.useCase

import androidx.lifecycle.MutableLiveData
import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.data.local.model.Priority
import com.example.doubletapcourse.domain.model.HabitDomain
import javax.inject.Inject

class FilterUseCase @Inject constructor() {
    operator fun invoke(
        nameFilter: String?,
        priorityFilter: Int?,
        currentTypeHabits: List<HabitDomain>,
    ): List<HabitDomain> {
        nameFilter?.let { name ->
            return currentTypeHabits.filter { it.name == name }
        }
        priorityFilter?.let { priority ->
            return currentTypeHabits.filter { it.priority == priority }
        }
        return currentTypeHabits
    }
}