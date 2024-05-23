package com.example.doubletapcourse.di.module


import com.example.data.HabitRepositoryImp
import com.example.data.local.HabitDao
import com.example.data.remote.HabitAPI
import com.example.doubletapcourse.di.component.HabitComponent
import com.example.doubletapcourse.domain.HabitRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(subcomponents = [HabitComponent::class])
class HabitSubModule {

    @Provides
    fun getRepositoryImpl(habitAPI: HabitAPI, habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImp(habitAPI, habitDao)
    }

    @Singleton
    @Provides
    fun getHabitApi(retrofit: Retrofit): HabitAPI {
        return retrofit.create(HabitAPI::class.java)
    }
}