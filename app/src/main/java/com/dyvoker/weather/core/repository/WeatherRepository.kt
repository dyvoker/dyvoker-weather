package com.dyvoker.weather.core.repository

import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.DailyWeatherData

interface WeatherRepository {
    suspend fun getCurrentWeather(coordinates: MapPoint): CurrentWeatherData
    suspend fun getForecastWeather(coordinates: MapPoint): List<DailyWeatherData>
}