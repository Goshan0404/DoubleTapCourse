package com.example.doubletapcourse.di.module

import android.content.Context
import androidx.room.Room
import com.example.data.local.HabitDB
import com.example.data.local.HabitDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule(private val context: Context) {



    @Singleton
    @Provides
    fun getDb(context: Context): HabitDB {
        return Room.databaseBuilder(
            context.applicationContext,
            HabitDB::class.java,
            "habit_db"
        ).build()
    }

    @Provides
    fun getHabitDao(habitDB: HabitDB): HabitDao {
        return habitDB.habitDao()
    }



    @Provides
    fun getContext(): Context = context

}