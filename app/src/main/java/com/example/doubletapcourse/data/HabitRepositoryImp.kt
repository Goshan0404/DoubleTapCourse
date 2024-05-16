package com.example.doubletapcourse.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.example.doubletapcourse.data.local.HabitDao
import com.example.doubletapcourse.data.remote.HabitAPI
import com.example.doubletapcourse.data.local.model.Habit
import com.example.doubletapcourse.data.local.model.Type
import com.example.doubletapcourse.data.remote.model.ErrorResponse
import com.example.doubletapcourse.domain.HabitRepository
import com.example.doubletapcourse.domain.model.HabitDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HabitRepositoryImp(private val habitApi: HabitAPI, private val habitDao: HabitDao) :
    HabitRepository {

    private val errorType = object : TypeToken<ErrorResponse>() {}.type

    override val habits = habitDao.getHabits().mapLatest { it.map { it.toHabitDomain() } }

    override suspend fun save(habit: HabitDomain) {

        withContext(Dispatchers.IO) {
            val response = habitApi.putHabits(habit.toRemoteHabit())
            if (response.isSuccessful) {
                val id = response.body()?.uid

                if (habit.id.isEmpty()) {
                    habit.id = id!!
                }

            } else {
                val errorResponse: ErrorResponse? =
                    Gson().fromJson(response.errorBody()!!.charStream(), errorType)
                Log.e("SAVE", "SAVE ERROR: ${errorResponse!!.message}")
            }

            habitDao.insert(habit.toLocalHabit())
        }
    }

    override suspend fun updateHabits() {
        withContext(Dispatchers.IO) {
            val response = habitApi.getHabits()
            if (response.isSuccessful) {
                val habitsRemote = response.body()

                habitsRemote?.forEach {
                    habitDao.insert(it.toHabit())
                }
            } else {
                val errorResponse: ErrorResponse? =
                    Gson().fromJson(response.errorBody()!!.charStream(), errorType)

                Log.e("GET", "GET ERROR: ${errorResponse!!.message}")
            }
        }
    }


    override suspend fun getTypeHabits(type: Int): List<HabitDomain> {

        return withContext(Dispatchers.IO) {
            habitDao.getHabitType(type.toString()).map { it.toHabitDomain() }
        }
    }

    override  fun getHabitById(id: String): Flow<HabitDomain?> {
        return habitDao.geHabitById(id).mapLatest {  it?.toHabitDomain() }

    }
}