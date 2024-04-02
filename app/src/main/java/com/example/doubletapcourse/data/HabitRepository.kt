package com.example.doubletapcourse.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.doubletapcourse.di.App
import com.example.doubletapcourse.domain.model.Habit
import com.example.doubletapcourse.domain.model.Type
import com.example.doubletapcourse.data.remote.ProviderHabitAPI
import com.example.doubletapcourse.data.remote.model.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HabitRepository(application: Application) {
    private var db = (application as App).getDb()
    private val habitDao = db.habitDao()
    private val habitApi = ProviderHabitAPI.habitApi

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