package com.example.doubletapcourse.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.doubletapcourse.domain.model.Habit

@Database(entities = [Habit::class], version = 1)
abstract class HabitDB : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {

        private var instance: HabitDB? = null

        fun getDB(context: Context): HabitDB {

            synchronized(this) {
                if (instance != null)
                    return instance as HabitDB
                else {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HabitDB::class.java,
                        "habit_db"
                    ).allowMainThreadQueries().build()
                    return instance as HabitDB
                }

            }
        }
    }
}