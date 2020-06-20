package com.dyvoker.weather.weather

import com.dyvoker.weather.core.DarkSkyApiService
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.WeatherItemData
import com.dyvoker.weather.core.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: DarkSkyApiService
): WeatherRepository {

    override suspend fun getCurrentWeather(coordinates: MapPoint): WeatherItemData {
        return WeatherItemData("rain", -1, 10101010)
    }

    override suspend fun getForecastWeather(coordinates: MapPoint): List<WeatherItemData> {
        return listOf(WeatherItemData("rain", -1, 10101010))
    }
}