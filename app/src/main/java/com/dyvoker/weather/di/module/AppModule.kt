package com.dyvoker.weather.di.module

import android.content.Context
import com.dyvoker.weather.common.App
import com.dyvoker.weather.core.AppConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideContext(): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideAppConfig(): AppConfig = AppConfig.Default()
}