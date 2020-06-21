package com.dyvoker.weather.weather

import com.dyvoker.weather.core.DarkSkyApiService
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.repository.WeatherRepository
import retrofit2.await

class WeatherRepositoryImpl(
    private val api: DarkSkyApiService
): WeatherRepository {

    override suspend fun getCurrentWeather(coordinates: MapPoint): CurrentWeatherData {
        return api.getCurrentWeather(
            coordinates.latitude.toString(),
            coordinates.longitude.toString()
        ).await().currentWeather
    }

    override suspend fun getForecastWeather(coordinates: MapPoint): List<DailyWeatherData> {
        return api.getForecastWeather(
            coordinates.latitude.toString(),
            coordinates.longitude.toString()
        ).await().dailyForecastData.list
    }
}