package com.example.doubletapcourse.di.factory

import com.example.doubletapcourse.di.component.ActivityScope
import com.example.doubletapcourse.presentation.viewModel.HabitListViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
@ActivityScope
interface HabitListViewModelFactory {
    fun create(): HabitListViewModel.SavedStateViewModelFactory
}