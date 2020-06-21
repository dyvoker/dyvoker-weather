package com.dyvoker.weather.di.module

import android.content.Context
import androidx.room.Room
import com.dyvoker.weather.core.DarkSkyApiService
import com.dyvoker.weather.core.repository.GlobalRepository
import com.dyvoker.weather.core.repository.room.AppDatabase
import com.dyvoker.weather.core.repository.WeatherRepository
import com.dyvoker.weather.weather.WeatherApiRepository
import com.dyvoker.weather.weather.WeatherCacheRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(
        context: Context,
        api: DarkSkyApiService,
        db: AppDatabase
    ): WeatherRepository =
        WeatherCacheRepository(
            WeatherApiRepository(context, api),
            db.currentWeatherDataDao(),
            db.dailyWeatherDataDao()
        )

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context) : AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "weather_repository"
        ).build()

    @Singleton
    @Provides
    fun provideGlobalRepository(context: Context) : GlobalRepository =
        GlobalRepository(context)
}