package com.example.doubletapcourse.di.component

import com.example.data.di.module.DataBaseModule
import com.example.doubletapcourse.di.module.HabitSubModule
import com.example.data.di.module.RetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataBaseModule::class, HabitSubModule::class, RetrofitModule::class])
interface ApplicationComponent {

    fun habitComponent(): HabitComponent.Factory
}