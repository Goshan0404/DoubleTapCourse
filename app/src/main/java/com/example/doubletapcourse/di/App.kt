package com.example.doubletapcourse.di

import android.app.Application
import com.example.doubletapcourse.data.dataBase.HabitDB

class App: Application() {
    private lateinit var db: HabitDB
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        db = HabitDB.getDB(this)
    }

    fun getDb() = db
}