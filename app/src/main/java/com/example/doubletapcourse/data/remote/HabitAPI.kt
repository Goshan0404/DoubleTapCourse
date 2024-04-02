package com.example.doubletapcourse.data.remote

import com.example.doubletapcourse.data.remote.model.HabitRemote
import com.example.doubletapcourse.data.remote.model.ID
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface HabitAPI {
    @GET("habit")
    @Headers("Authorization: a115cc8f-afc2-4462-8dbb-0a077aa2e85d")
    suspend fun getHabits(): Response<List<HabitRemote>>

    @PUT("habit")
    @Headers("Authorization: a115cc8f-afc2-4462-8dbb-0a077aa2e85d")
    suspend fun putHabits(@Body body: HabitRemote): Response<ID>
}
