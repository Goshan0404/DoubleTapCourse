package com.example.doubletapcourse.presentation.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.domain.useCase.FilterUseCase
import com.example.doubletapcourse.domain.useCase.GetAllHabitsUseCase
import com.example.doubletapcourse.domain.useCase.GetHabitsTypeUseCase
import com.example.doubletapcourse.domain.useCase.SaveHabitUseCase
import com.example.doubletapcourse.domain.useCase.UpdateHabitsUseCase
import com.example.doubletapcourse.presentation.fragments.HabitListFragment.HabitListFragmentState
import com.example.doubletapcourse.presentation.model.Habit
import com.example.doubletapcourse.presentation.model.Priority
import com.example.doubletapcourse.presentation.model.Type
import com.example.doubletapcourse.utli.toLocalHabit
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class HabitListViewModel @AssistedInject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val getHabitsTypeUseCase: GetHabitsTypeUseCase,
    private val updateHabits: UpdateHabitsUseCase,
    private val saveHabitUseCase: SaveHabitUseCase,
    private val filterUseCase: FilterUseCase,
) : ViewModel() {

    private val _positiveHabitsType = MutableSharedFlow<List<Habit>>()
    private val _negativeHabitsType = MutableSharedFlow<List<Habit>>()

    private var nameFilter = MutableSharedFlow<String>()
    private var priorityFilter = MutableSharedFlow<Priority>()

    private val _stateOfPositive =
        MutableSharedFlow<HabitListFragmentState>(
        )

    private val _stateOfNegative =
        MutableSharedFlow<HabitListFragmentState>(
        )

    init {
        viewModelScope.launch {
            updateHabits()
        }

        viewModelScope.launch {
            getAllHabitsUseCase().collect {

                _positiveHabitsType.emit(it.filter { it.type == Type.Useful.ordinal }
                    .map { it.toLocalHabit() })

                _negativeHabitsType.emit(it.filter { it.type == Type.UnUseful.ordinal }
                    .map { it.toLocalHabit() })
            }

        }

        viewModelScope.launch {

            nameFilter.collect { name ->
                launch {
                    launch {
                        _positiveHabitsType.emit(getHabitsTypeUseCase(Type.Useful.ordinal).map { it.toLocalHabit() })
                    }

                    launch {
                        _negativeHabitsType.emit(getHabitsTypeUseCase(Type.UnUseful.ordinal).map { it.toLocalHabit() })
                    }
                }.join()

                _positiveHabitsType.emit(
                    _positiveHabitsType
                        .mapLatest { it.filter { it.name == name } }.last()
                )

                _negativeHabitsType.emit(
                    _negativeHabitsType
                        .mapLatest { it.filter { it.name == name } }.last()
                )
            }
        }
    }

    fun getHabits(type: Type): Flow<List<Habit>> {
        return if (type == Type.Useful)
            _positiveHabitsType
        else
            _negativeHabitsType
    }

    fun getState(type: Type): MutableSharedFlow<HabitListFragmentState> {
        return if (type == Type.Useful)
            _stateOfPositive
        else
            _stateOfNegative
    }

    private suspend fun resetHabits() {
        _positiveHabitsType.emit(getHabitsTypeUseCase(Type.Useful.ordinal).map { it.toLocalHabit() })
        _negativeHabitsType.emit(getHabitsTypeUseCase(Type.UnUseful.ordinal).map { it.toLocalHabit() })
    }

    fun priorityChanged(text: CharSequence?) {
        if (text != null && text.toString().isNotEmpty())

            viewModelScope.launch {
                priorityFilter.emit(Priority.valueOf(text.toString()))
            }
    }

    fun nameSearchChanged(text: CharSequence?) {
        if (text != null && text.toString().isNotEmpty())

            viewModelScope.launch {
                nameFilter.emit(text.toString())
            }
    }

    fun doneButtonClicked(habit: Habit) {
        viewModelScope.launch {
            habit.count++
            if (habit.type == Type.UnUseful) {
                if (habit.count < habit.maxCount) {
                    _stateOfNegative.emit(HabitListFragmentState.MayDo(habit.maxCount - habit.count))
                    saveHabitUseCase(habit.toHabitDomain())
                } else
                    _stateOfNegative.emit(HabitListFragmentState.StopDo)
            } else {
                if (habit.count < habit.maxCount) {
                    _stateOfPositive.emit(HabitListFragmentState.MustDo(habit.maxCount - habit.count))
                    saveHabitUseCase(habit.toHabitDomain())
                } else {
                    _stateOfPositive.emit(HabitListFragmentState.Overfulfilled)
                    saveHabitUseCase(habit.toHabitDomain())
                }
            }
        }
    }

    class SavedStateViewModelFactory @AssistedInject constructor(
        private val getAllHabitsUseCase: GetAllHabitsUseCase,
        private val getHabitsTypeUseCase: GetHabitsTypeUseCase,
        private val updateHabits: UpdateHabitsUseCase,
        private val saveHabitUseCase: SaveHabitUseCase,
        private val filterUseCase: FilterUseCase,
    ) :
        AbstractSavedStateViewModelFactory() {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return HabitListViewModel(
                getAllHabitsUseCase,
                getHabitsTypeUseCase,
                updateHabits,
                saveHabitUseCase,
                filterUseCase
            ) as T
        }
    }
}