package com.example.data.di.module

import com.example.data.HabitRepositoryImp
import com.example.data.local.HabitDao
import com.example.data.remote.HabitAPI
import com.example.doubletapcourse.domain.HabitRepository
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

    @Provides
    fun getRepositoryImpl(habitAPI: HabitAPI, habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImp(habitAPI, habitDao)
    }

    @Singleton
    @Provides
    fun getHabitApi(retrofit: Retrofit): HabitAPI {
        return retrofit.create(HabitAPI::class.java)
    }

}