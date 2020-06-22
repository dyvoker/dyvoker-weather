package com.dyvoker.weather.core.repository

import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.data.Resource

interface WeatherRepository {
    suspend fun getCurrentWeather(point: MapPoint): Resource<CurrentWeatherData>
    suspend fun getForecastWeather(point: MapPoint): Resource<List<DailyWeatherData>>
}