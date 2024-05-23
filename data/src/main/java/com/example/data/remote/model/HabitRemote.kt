package com.example.data.remote.model

import com.example.data.local.HabitData
import com.example.doubletapcourse.domain.model.HabitDomain
import com.google.gson.annotations.SerializedName

data class HabitRemote(
    val color: Int,
    val count: Int,
    val date: Long,
    val description: String,
    @SerializedName("done_dates")
    val doneDates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String
) {


    fun toHabitData(): HabitData {
        return HabitData(count, date, description, frequency, priority, title, type, uid)
    }


}