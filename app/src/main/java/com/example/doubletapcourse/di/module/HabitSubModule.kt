package com.example.doubletapcourse.di.module


import com.example.doubletapcourse.di.component.HabitComponent
import dagger.Module

@Module(subcomponents = [HabitComponent::class])
class HabitSubModule