package com.example.doubletapcourse.presentation.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.R
import com.example.doubletapcourse.di.component.ActivityScope
import com.example.doubletapcourse.domain.useCase.GetHabitByIdUseCase
import com.example.doubletapcourse.domain.useCase.SaveHabitUseCase
import com.example.doubletapcourse.presentation.fragments.AddHabitFragment
import com.example.doubletapcourse.presentation.fragments.AddHabitFragment.AddHabitFragmentState
import com.example.doubletapcourse.presentation.model.Habit
import com.example.doubletapcourse.presentation.model.Interval
import com.example.doubletapcourse.presentation.model.Priority
import com.example.doubletapcourse.presentation.model.Type
import com.example.doubletapcourse.utli.toHabitDomain
import com.example.doubletapcourse.utli.toLocalHabit
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class AddHabitViewModel @Inject constructor(
    private val saveHabitUseCase: SaveHabitUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val handle: SavedStateHandle
) :
    ViewModel() {

    private var currentHabit =
        if (handle.get<String>(AddHabitFragment.HABIT_ID) != null)
            getHabitByIdUseCase(handle[AddHabitFragment.HABIT_ID]!!).mapLatest { it?.toLocalHabit() }
        else flowOf(
            Habit(
                "",
                "",
                "",
                Type.NotChosen,
                Priority.NotChosen,
                0,
                Interval.NotChosen,
                0,
                10
            )
        )


    private val _state: MutableStateFlow<AddHabitFragmentState> = MutableStateFlow(
        AddHabitFragmentState.NoState
    )
    val state: StateFlow<AddHabitFragmentState> = _state

    init {
        viewModelScope.launch {

            currentHabit.collect {
                _state.value = AddHabitFragmentState.HabitExist(it!!)
            }
        }
    }

    fun saveHabit() {
        viewModelScope.launch {
            currentHabit.first()?.let {
                val habit = it
                if (habit.name.isEmpty() || habit.description.isEmpty() || habit.type == Type.NotChosen
                    || habit.priority == Priority.NotChosen || habit.intervalCount == 0 || habit.interval == Interval.NotChosen
                ) {
                    _state.value = AddHabitFragmentState.EmptyFields
                    return@launch
                }

                if (!saveHabitUseCase(it.toHabitDomain()))
                    _state.value = AddHabitFragmentState.Error
                _state.value = AddHabitFragmentState.NavigateUp
            }
        }
    }

    fun countChanged(text: CharSequence?) {
        viewModelScope.launch {
            if (text != null)
                currentHabit =
                    currentHabit.map { it?.copy(intervalCount = text.toString().toInt()) }
        }
    }

    fun nameChanged(text: CharSequence?) {
        viewModelScope.launch {
            if (text != null)
                currentHabit = currentHabit.map { it?.copy(name = text.toString()) }
        }

    }

    fun typeChanged(id: Int) {
        viewModelScope.launch {
            when (id) {
                R.id.useful_radioButton -> currentHabit =
                    currentHabit.map { it?.copy(type = Type.Useful) }

                R.id.unuseful_radioButton -> currentHabit =
                    currentHabit.map { it?.copy(type = Type.UnUseful) }
            }
        }
    }

    fun descriptionChanged(text: CharSequence?) {
        viewModelScope.launch {
            if (text != null)
                currentHabit = currentHabit.map { it?.copy(description = text.toString()) }
        }
    }

    fun priorityChanged(text: CharSequence?) {
        viewModelScope.launch {
            if (text != null)
                currentHabit =
                    currentHabit.map { it?.copy(priority = Priority.valueOf(text.toString())) }
        }
    }

    fun intervalChanged(text: CharSequence?) {
        viewModelScope.launch {
            if (text != null)
                currentHabit =
                    currentHabit.map { it?.copy(interval = Interval.valueOf(text.toString())) }
        }
    }

    class ViewModelFactory @AssistedInject constructor(
        private val savedUseCase: SaveHabitUseCase,
        private val getHabitByIdUseCase: GetHabitByIdUseCase
    ) :
        AbstractSavedStateViewModelFactory() {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return AddHabitViewModel(savedUseCase, getHabitByIdUseCase, handle) as T
        }

    }

}