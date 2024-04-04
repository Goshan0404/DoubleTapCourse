package com.example.doubletapcourse.di.component

import com.example.doubletapcourse.presentation.fragments.AddHabitFragment
import com.example.doubletapcourse.presentation.fragments.HabitListFragment
import dagger.Subcomponent
import javax.inject.Scope

@ActivityScope
@Subcomponent
interface HabitComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HabitComponent
    }
    fun inject(fragment: HabitListFragment)
    fun inject(fragment: AddHabitFragment)
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope
