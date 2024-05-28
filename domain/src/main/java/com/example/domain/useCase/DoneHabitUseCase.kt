package com.example.domain.useCase

import com.example.doubletapcourse.domain.model.HabitDomain
import com.example.doubletapcourse.domain.useCase.SaveHabitUseCase
import javax.inject.Inject

class DoneHabitUseCase @Inject constructor(private val saveHabitUseCase: SaveHabitUseCase) {
    private val NEGATIVE_TYPE_NUMBER = 1
    suspend operator fun invoke(habit: HabitDomain): DoneState {
        habit.count++
        saveHabitUseCase(habit)

        return if (habit.type == NEGATIVE_TYPE_NUMBER) {
            if (habit.count < habit.maxCount) {
                DoneState.NegativeHabitDoneLess(habit.maxCount - habit.count)
            } else
                DoneState.NegativeHabitDoneOverFlow
        } else {
            if (habit.count < habit.maxCount) {
                DoneState.PositiveHabitDoneLess(habit.maxCount - habit.count)
            } else {
                DoneState.PositiveHabitDoneOverFlow
            }
        }
    }

    sealed class DoneState() {
        data object PositiveHabitDoneOverFlow : DoneState()
        class PositiveHabitDoneLess(val count: Int) : DoneState()
        data object NegativeHabitDoneOverFlow : DoneState()
        class NegativeHabitDoneLess(val count: Int) : DoneState()
        data object NoState : DoneState()

    }
}