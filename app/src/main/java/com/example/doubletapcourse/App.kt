package com.example.doubletapcourse

import android.app.Application
import com.example.doubletapcourse.di.component.ApplicationComponent
import com.example.doubletapcourse.di.component.DaggerApplicationComponent
import com.example.doubletapcourse.di.module.ApplicationModule

class App : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }
}