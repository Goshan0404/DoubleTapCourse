package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.remote.model.HabitRemote

@Database(entities = [HabitData::class], version = 1)
abstract class HabitDB : RoomDatabase() {
    abstract fun habitDao(): HabitDao

}