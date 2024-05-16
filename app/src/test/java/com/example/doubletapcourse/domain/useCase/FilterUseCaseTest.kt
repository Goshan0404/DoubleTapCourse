package com.example.doubletapcourse.domain.useCase

import com.example.doubletapcourse.domain.model.HabitDomain
import org.junit.Assert

import org.junit.jupiter.api.Test

class FilterUseCaseTest{
    @Test
    operator fun invoke(
        nameFilter: String?,
        priorityFilter: Int?,
        currentTypeHabits: List<HabitDomain>,
    ): List<HabitDomain> {
        Assert.assertEquals(4, 2 + 2)
        nameFilter?.let { name ->
            return currentTypeHabits.filter { it.name == name }
        }
        priorityFilter?.let { priority ->
            return currentTypeHabits.filter { it.priority == priority }
        }
        return currentTypeHabits
    }
}