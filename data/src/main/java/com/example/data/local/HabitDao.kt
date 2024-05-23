package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.remote.model.HabitRemote
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM HabitData")
    fun getHabits(): Flow<List<HabitData>>

    @Query("SELECT * FROM HabitData WHERE type = :type")
    suspend fun getHabitType(type: String): List<HabitData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: HabitData)

    @Query("SELECT * FROM HabitData WHERE uid = :id")
    fun geHabitById(id: String): Flow<HabitData?>
}