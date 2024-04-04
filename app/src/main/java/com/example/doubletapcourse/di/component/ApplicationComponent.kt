package com.example.doubletapcourse.di.component

import com.example.doubletapcourse.di.module.ApplicationModule
import com.example.doubletapcourse.di.module.HabitSubComponentModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, HabitSubComponentModule::class])
interface ApplicationComponent {

    fun habitComponent(): HabitComponent.Factory
}