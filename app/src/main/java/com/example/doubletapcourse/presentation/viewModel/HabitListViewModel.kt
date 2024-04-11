package com.example.doubletapcourse.presentation.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.domain.useCase.FilterUseCase
import com.example.doubletapcourse.domain.useCase.GetAllHabitsUseCase
import com.example.doubletapcourse.domain.useCase.GetHabitsTypeUseCase
import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.data.local.model.Priority
import com.example.doubletapcourse.data.local.model.Type
import com.example.doubletapcourse.presentation.fragments.HabitListFragment
import com.example.doubletapcourse.presentation.fragments.HabitListFragment.HabitListFragmentState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitListViewModel @AssistedInject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val getHabitsTypeUseCase: GetHabitsTypeUseCase,
    private val filterUseCase: FilterUseCase,
    @Assisted handle: SavedStateHandle,
) : ViewModel() {

    private val type: Type = handle[HabitListFragment.IS_POSITIVE_HABITS]!!

    private val _currentHabitsType = MutableLiveData<List<Habit>>()
    var currentTypeHabits: LiveData<List<Habit>> = _currentHabitsType

    private var nameFilter: String? = null
    private var priorityFilter: String? = null

    private val _state: MutableStateFlow<HabitListFragmentState> =
        MutableStateFlow(
            HabitListFragmentState.NoState
        )
    val state: StateFlow<HabitListFragmentState> = _state


    init {
        viewModelScope.launch {
            getAllHabitsUseCase().collect {
                _currentHabitsType.value = it.filter { it.type == type.toInt() }.map { it.toLocalHabit() }
            }
        }
    }

    fun filterHabits() {
        viewModelScope.launch {
            resetHabits()
            val priorityInt = if (Priority.valueOf(priorityFilter!!) == Priority.Low) 0 else 1

            filterUseCase(nameFilter, priorityInt, _currentHabitsType.value!!.map { it.toHabitDomain() })
        }
    }

    private suspend fun resetHabits() {
        _currentHabitsType.value = getHabitsTypeUseCase(type.toInt()).map { it.toLocalHabit() }
    }

    fun priorityChanged(text: CharSequence?) {
        if (text != null)
            priorityFilter = text.toString()
    }

    fun nameSearchChanged(text: CharSequence?) {
        if (text != null)
            nameFilter = text.toString()
    }

    fun doneButtonClicked(habit: Habit) {
        habit.count++
        if (habit.type == Type.UnUseful)
            if (habit.count < habit.maxCount)
                _state.value = HabitListFragmentState.MayDo(habit.maxCount - habit.count)
            else
                _state.value = HabitListFragmentState.StopDo
        else
            if (habit.count < habit.maxCount)
                _state.value = HabitListFragmentState.MustDo(habit.maxCount - habit.count)
            else
                _state.value = HabitListFragmentState.Overfulfilled
    }

    class SavedStateViewModelFactory @AssistedInject constructor(
        private val getAllHabitsUseCase: GetAllHabitsUseCase,
        private val getHabitsTypeUseCase: GetHabitsTypeUseCase,
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
                filterUseCase,
                handle
            ) as T
        }
    }
}