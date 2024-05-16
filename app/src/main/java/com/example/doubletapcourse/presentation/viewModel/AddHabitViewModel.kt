package com.example.doubletapcourse.presentation.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.R
import com.example.doubletapcourse.di.component.ActivityScope
import com.example.doubletapcourse.domain.useCase.SaveHabitUseCase
import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.data.local.model.Interval
import com.example.doubletapcourse.data.local.model.Priority
import com.example.doubletapcourse.data.local.model.Type
import com.example.doubletapcourse.domain.useCase.GetHabitByIdUseCase
import com.example.doubletapcourse.presentation.fragments.AddHabitFragment
import com.example.doubletapcourse.presentation.fragments.AddHabitFragment.AddHabitFragmentState
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class AddHabitViewModel @Inject constructor(
    private val saveHabitUseCase: SaveHabitUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val handle: SavedStateHandle
) :
    ViewModel() {

    private var currentHabit: MutableLiveData<Habit?> =
        MutableLiveData(
            Habit(
                "",
                "",
                "",
                Type.NotChosen,
                Priority.NotChosen,
                0,
                Interval.NotChosen,
                0,
                0
            )
        )

    private val _state: MutableStateFlow<AddHabitFragmentState> = MutableStateFlow(
        AddHabitFragmentState.NoState
    )
    val state: StateFlow<AddHabitFragmentState> = _state

    init {
        viewModelScope.launch {
            val id: String? = handle[AddHabitFragment.HABIT_ID]
            id?.let {
                currentHabit.postValue(getHabitByIdUseCase(it).asLiveData().value!!.toLocalHabit())
                    currentHabit.value?.let {
                        _state.value = AddHabitFragmentState.HabitExist(it)
                    }
            }
        }
    }

    fun saveHabit() {
        viewModelScope.launch {
            val habit = currentHabit.value!!
            if (habit.name.isEmpty() || habit.description.isEmpty() || habit.type == Type.NotChosen
                || habit.priority == Priority.NotChosen || habit.intervalCount == 0 || habit.interval == Interval.NotChosen
            ) {
                _state.value = AddHabitFragmentState.EmptyFields
                return@launch
            }

            currentHabit.value?.let {
                saveHabitUseCase(it.toHabitDomain())
                _state.value = AddHabitFragmentState.NavigateUp
            }
        }
    }

    fun countChanged(text: CharSequence?) {
        if (text != null)
            currentHabit.value?.intervalCount = text.toString().toInt()
    }

    fun nameChanged(text: CharSequence?) {
        if (text != null)
            currentHabit.value?.name = text.toString()

    }

    fun typeChanged(id: Int) {
        when (id) {
            R.id.useful_radioButton -> currentHabit.value?.type = Type.Useful
            R.id.unuseful_radioButton -> currentHabit.value?.type = Type.UnUseful
        }
    }

    fun descriptionChanged(text: CharSequence?) {
        if (text != null)
            currentHabit.value?.description = text.toString()
    }

    fun priorityChanged(text: CharSequence?) {
        if (text != null)
            currentHabit.value?.priority = Priority.valueOf(text.toString())
    }

    fun intervalChanged(text: CharSequence?) {
        if (text != null)
            currentHabit.value?.interval = Interval.valueOf(text.toString())
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