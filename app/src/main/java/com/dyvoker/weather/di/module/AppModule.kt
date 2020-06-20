package com.dyvoker.weather.di.module

import android.content.Context
import com.dyvoker.weather.common.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideContext(): Context = app.applicationContext
}