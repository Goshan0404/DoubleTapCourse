package com.example.doubletapcourse.di.factory

import androidx.lifecycle.SavedStateHandle
import com.example.doubletapcourse.di.component.ActivityScope
import com.example.doubletapcourse.presentation.viewModel.HabitListViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
@ActivityScope
interface ViewModelFactory {
    fun create(): HabitListViewModel.SavedStateViewModelFactory
}