package com.example.doubletapcourse.di.module

import android.content.Context
import androidx.room.Room
import com.example.doubletapcourse.data.HabitRepositoryImp
import com.example.doubletapcourse.data.local.HabitDB
import com.example.doubletapcourse.data.local.HabitDao
import com.example.doubletapcourse.data.remote.HabitAPI
import com.example.doubletapcourse.domain.HabitRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {

        val client = OkHttpClient().newBuilder()
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

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/").build()
    }

    @Singleton
    @Provides
    fun getHabitApi(retrofit: Retrofit): HabitAPI {
        return retrofit.create(HabitAPI::class.java)
    }

    @Singleton
    @Provides
    fun getDb(context: Context): HabitDB {
        return  Room.databaseBuilder(
            context.applicationContext,
            HabitDB::class.java,
            "habit_db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun getHabitDao(habitDB: HabitDB): HabitDao {
        return habitDB.habitDao()
    }

    @Provides
    fun getRepositoryImpl(habitAPI: HabitAPI, habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImp(habitAPI, habitDao)
    }


    @Provides
    fun getContext(): Context = context

}