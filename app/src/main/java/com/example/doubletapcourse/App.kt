package com.example.doubletapcourse

import android.app.Application
import com.example.doubletapcourse.data.dataBase.HabitDB

class App: Application() {
    private lateinit var db: HabitDB

    companion object {
        private lateinit var instance: App

        fun getInstance() = instance
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        db = HabitDB.getDB(this)
    }

    fun getDb() = db
}