package com.dyvoker.weather.weather

import android.content.Context
import com.dyvoker.weather.common.getCurrentLanguage
import com.dyvoker.weather.core.DarkSkyApiService
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.repository.WeatherRepository
import retrofit2.await

class WeatherApiRepository(
    private val context: Context,
    private val api: DarkSkyApiService
) : WeatherRepository {

    override suspend fun getCurrentWeather(point: MapPoint): CurrentWeatherData =
        api.getCurrentWeather(
            point.latitude.toString(),
            point.longitude.toString(),
            context.getCurrentLanguage()
        ).await().currentWeather.apply {
            latitude = point.latitude
            longitude = point.longitude
            creationTimestamp = System.currentTimeMillis()
        }

    override suspend fun getForecastWeather(point: MapPoint): List<DailyWeatherData> =
        api.getForecastWeather(
            point.latitude.toString(),
            point.longitude.toString(),
            context.getCurrentLanguage()
        ).await().dailyForecastData.list.apply {
            val timestamp = System.currentTimeMillis()
            forEach {
                it.apply {
                    latitude = point.latitude
                    longitude = point.longitude
                    creationTimestamp = timestamp
                }
            }
        }
}