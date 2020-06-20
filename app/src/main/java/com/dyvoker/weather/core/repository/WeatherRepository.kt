package com.dyvoker.weather.core.repository

import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.WeatherItemData

interface WeatherRepository {
    suspend fun getCurrentWeather(coordinates: MapPoint): WeatherItemData
    suspend fun getForecastWeather(coordinates: MapPoint): List<WeatherItemData>
}