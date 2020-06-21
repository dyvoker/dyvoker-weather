package com.dyvoker.weather.weather

import com.dyvoker.weather.core.DarkSkyApiService
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.repository.WeatherRepository
import retrofit2.await

class WeatherApiRepository(
    private val api: DarkSkyApiService
): WeatherRepository {

    override suspend fun getCurrentWeather(point: MapPoint): CurrentWeatherData {
        return api.getCurrentWeather(
            point.latitude.toString(),
            point.longitude.toString()
        ).await().currentWeather
    }

    override suspend fun getForecastWeather(point: MapPoint): List<DailyWeatherData> {
        return api.getForecastWeather(
            point.latitude.toString(),
            point.longitude.toString()
        ).await().dailyForecastData.list
    }
}