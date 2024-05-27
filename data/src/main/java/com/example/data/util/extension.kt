package com.example.data.util

import com.example.data.local.HabitData
import com.example.data.remote.model.HabitRemote
import com.example.doubletapcourse.domain.model.HabitDomain
import java.util.Date

fun HabitDomain.toHabitData(): HabitData {

    return HabitData(
        count,
        Date().time,
        description,
        (intervalCount.toString() + interval.toString()).toInt(),
        priority,
        name,
        type,
        id
    )
}


fun HabitDomain.toHabitRemote(): HabitRemote {

    return HabitRemote(
        0,
        count,
        Date().time,
        description,
        listOf(),
        (intervalCount.toString() + interval.toString()).toInt(),
        priority,
        name,
        type,
        id
    )
}