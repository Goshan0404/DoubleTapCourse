package com.example.domain.useCase

sealed class DoneState() {
    data object PositiveHabitDoneOverFlow : DoneState()
    class PositiveHabitDoneLess(val count: Int) : DoneState()
    data object NegativeHabitDoneOverFlow : DoneState()
    class NegativeHabitDoneLess(val count: Int) : DoneState()
}