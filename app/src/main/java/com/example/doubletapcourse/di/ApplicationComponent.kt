package com.example.doubletapcourse.di

import com.example.doubletapcourse.presentation.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

}