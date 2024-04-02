package com.example.doubletapcourse.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder {

    private var client = OkHttpClient().newBuilder()
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryCount = 0
            while (!response.isSuccessful) {
                tryCount++
                response = chain.proceed(request)
            }
            response
        })
        .build()

    fun getRetrofit(): Retrofit {

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/").build()
    }
}