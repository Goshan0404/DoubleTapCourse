package com.example.doubletapcourse.data.remote

object ProviderHabitAPI {
    val habitApi: HabitAPI by lazy { RetrofitBuilder().getRetrofit().create(HabitAPI::class.java) }
}