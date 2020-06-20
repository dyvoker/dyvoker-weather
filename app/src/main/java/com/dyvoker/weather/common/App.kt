package com.dyvoker.weather.common

import android.app.Application
import com.dyvoker.weather.di.component.AppComponent
import com.dyvoker.weather.di.component.DaggerAppComponent
import com.dyvoker.weather.di.module.AppModule
import com.dyvoker.weather.di.module.RetrofitModule

class App: Application() {

    companion object {
        private lateinit var instance: App
        private lateinit var appComponent: AppComponent

        fun appComponent() = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.factory().create(
            AppModule(this),
            RetrofitModule("https://api.darksky.net/")
        )
        appComponent.inject(this)
    }
}