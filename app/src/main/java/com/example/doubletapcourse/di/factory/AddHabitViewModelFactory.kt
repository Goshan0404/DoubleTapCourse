package com.example.doubletapcourse.di.factory

import com.example.doubletapcourse.di.component.ActivityScope
import com.example.doubletapcourse.presentation.viewModel.AddHabitViewModel
import com.example.doubletapcourse.presentation.viewModel.HabitListViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
@ActivityScope
interface AddHabitViewModelFactory {
    fun create(): AddHabitViewModel.ViewModelFactory

}