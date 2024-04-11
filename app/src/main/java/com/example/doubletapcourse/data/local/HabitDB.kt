package com.example.doubletapcourse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doubletapcourse.data.local.model.Habit

@Database(entities = [Habit::class], version = 1)
abstract class HabitDB : RoomDatabase() {
    abstract fun habitDao(): HabitDao

}