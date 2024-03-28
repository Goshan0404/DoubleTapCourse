package com.example.doubletapcourse

import android.app.Application
import com.example.doubletapcourse.data.dataBase.HabitDB

class App: Application() {
    private lateinit var db: HabitDB

    override fun onCreate() {
        super.onCreate()
        db = HabitDB.getDB(this)
    }

    fun getDb() = db
}