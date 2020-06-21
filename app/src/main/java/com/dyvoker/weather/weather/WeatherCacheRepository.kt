package com.dyvoker.weather.weather

import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.repository.WeatherRepository

class WeatherCacheRepository(
    private val weatherRepository: WeatherRepository
) : WeatherRepository {

    override suspend fun getCurrentWeather(point: MapPoint): CurrentWeatherData {
        return weatherRepository.getCurrentWeather(point)
    }

    override suspend fun getForecastWeather(point: MapPoint): List<DailyWeatherData> {
        return weatherRepository.getForecastWeather(point)
    }
}