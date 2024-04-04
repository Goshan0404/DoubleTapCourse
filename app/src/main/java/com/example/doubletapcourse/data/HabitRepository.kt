package com.example.doubletapcourse.data

import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.doubletapcourse.data.dataBase.HabitDao
import com.example.doubletapcourse.data.remote.HabitAPI
import com.example.doubletapcourse.domain.model.Habit
import com.example.doubletapcourse.domain.model.Type
import com.example.doubletapcourse.data.remote.model.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class HabitRepository @Inject constructor(private val habitApi: HabitAPI, private val habitDao: HabitDao) {

    private val errorType = object : TypeToken<ErrorResponse>() {}.type

    val habits = habitDao.getHabits().asLiveData()

    init {
        CoroutineScope(Dispatchers.Unconfined).launch {
            val response = habitApi.getHabits()
            if (response.isSuccessful) {
                val habitsRemote = response.body()?.map { it.toHabit() }

                habitsRemote?.forEach {
                    habitDao.insert(it)
                }
            } else {
                val errorResponse: ErrorResponse? =
                    Gson().fromJson(response.errorBody()!!.charStream(), errorType)

                Log.e("GET", "GET ERROR: ${errorResponse!!.message}")
            }


        }
    }

    suspend fun save(habit: Habit) {

        val response = habitApi.putHabits(habit.toHabitRemote())
        if (response.isSuccessful) {
            val id = response.body()?.uid

            if (habit.id.isEmpty()) {
                habit.id = id!!
            }
            habitDao.insert(habit)

        } else {
            val errorResponse: ErrorResponse? =
                Gson().fromJson(response.errorBody()!!.charStream(), errorType)

            Log.e("SAVE", "SAVE ERROR: ${errorResponse!!.message}")
        }
    }

    suspend fun getTypeHabits(type: Type): List<Habit> {

        return habitDao.getHabitType(type.toString())

    }
}