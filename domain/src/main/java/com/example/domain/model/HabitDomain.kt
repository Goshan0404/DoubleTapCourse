package com.example.doubletapcourse.domain.model

data class HabitDomain(
    var id: String,
    var name: String,
    var description: String,
    var type: Int,
    var priority: Int,
    var intervalCount: Int,
    var interval: Int,
    var count: Int,
    var maxCount: Int
)