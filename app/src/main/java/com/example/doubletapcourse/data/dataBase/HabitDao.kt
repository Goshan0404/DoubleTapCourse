package com.example.doubletapcourse.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Type

@Dao
interface HabitDao {
    @Query("SELECT * FROM Habit")
    fun getHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM Habit WHERE type = :type")
    suspend fun getHabitType(type: String): List<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit)
}