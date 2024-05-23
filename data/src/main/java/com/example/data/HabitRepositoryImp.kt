package com.example.data

import android.util.Log
import com.example.data.local.HabitDao
import com.example.data.local.HabitData
import com.example.data.remote.HabitAPI
import com.example.data.remote.model.ErrorResponse
import com.example.data.remote.model.HabitRemote
import com.example.doubletapcourse.domain.HabitRepository
import com.example.doubletapcourse.domain.model.HabitDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date


class HabitRepositoryImp(private val habitApi: HabitAPI, private val habitDao: HabitDao) :
    HabitRepository {

    private val errorType = object : TypeToken<ErrorResponse>() {}.type

    override val habits = habitDao.getHabits().map { it.map { it.toHabitDomain() } }

    override suspend fun save(habit: HabitDomain): Boolean {
        var success = false

        withContext(Dispatchers.IO) {
            try {
                val response = habitApi.putHabits(habit.toHabitRemote())
                if (response.isSuccessful) {
                    success = true

                    val id = response.body()?.uid

                    if (habit.id.isEmpty()) {
                        habit.id = id!!
                    }
                    habitDao.insert(habit.toHabitData())
                } else {
                    val errorResponse: ErrorResponse? =
                        Gson().fromJson(response.errorBody()!!.charStream(), errorType)
                    Log.e("SAVE", "SAVE ERROR: ${errorResponse!!.message}")
                }
            } catch (ecxeptoin: Exception) {
                success = false
//                Log.e("SAVE", "SAVE ERROR: ${errorResponse!!.message}")
            }
        }
        return success

    }

    override suspend fun updateHabits(): Boolean {
        var success = false
        withContext(Dispatchers.IO) {
            try {
                val response = habitApi.getHabits()
                if (response.isSuccessful) {
                    success = true
                    val habitsRemote = response.body()

                    habitsRemote?.forEach {
                        habitDao.insert(it.toHabitData())
                    }
                } else {
                    val errorResponse: ErrorResponse? =
                        Gson().fromJson(response.errorBody()!!.charStream(), errorType)

                    Log.e("GET", "GET ERROR: ${errorResponse!!.message}")
                }
            } catch (e: Exception) {
                success = false
            }
        }
        return success
    }


    override suspend fun getTypeHabits(type: Int): List<HabitDomain> {

        return withContext(Dispatchers.IO) {
            habitDao.getHabitType(type.toString()).map { it.toHabitDomain() }
        }
    }

    override fun getHabitById(id: String): Flow<HabitDomain?> {
        return habitDao.geHabitById(id).map { it?.toHabitDomain() }

    }
}

fun HabitDomain.toHabitData(): HabitData {

    return HabitData(intervalCount, Date().time, description, interval, priority, name, type, id)
}


fun HabitDomain.toHabitRemote(): HabitRemote {

    return HabitRemote(0, intervalCount, Date().time, description, listOf(), interval, priority, name, type, id)
}