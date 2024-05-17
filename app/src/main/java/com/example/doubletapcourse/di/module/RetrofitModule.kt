package com.example.doubletapcourse.di.module

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {


    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {

        val client = OkHttpClient().newBuilder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                var response = chain.proceed(request)
                while (!response.isSuccessful) {
                    Thread.sleep(2000L)
                    response = chain.proceed(request)
                }
                response
            })
            .build()

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/").build()
    }

}