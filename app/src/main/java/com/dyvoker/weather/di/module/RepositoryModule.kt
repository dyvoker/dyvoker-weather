package com.dyvoker.weather.di.module

import com.dyvoker.weather.core.DarkSkyApiService
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
    fun provideWeatherRepository(api: DarkSkyApiService): WeatherRepository =
        WeatherCacheRepository(WeatherApiRepository(api))
}