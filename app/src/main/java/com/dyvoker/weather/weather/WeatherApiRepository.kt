package com.dyvoker.weather.weather

import android.content.Context
import com.dyvoker.weather.common.getCurrentLanguage
import com.dyvoker.weather.core.DarkSkyApiService
import com.dyvoker.weather.core.data.*
import com.dyvoker.weather.core.repository.WeatherRepository
import retrofit2.await

class WeatherApiRepository(
    private val context: Context,
    private val api: DarkSkyApiService
) : WeatherRepository {

    private val responseHandler = ResponseHandler()

    override suspend fun getCurrentWeather(point: MapPoint): Resource<CurrentWeatherData> {
        return try {
            val weather = api.getCurrentWeather(
                point.latitude.toString(),
                point.longitude.toString(),
                context.getCurrentLanguage()
            ).await().currentWeather
            weather.apply {
                latitude = point.latitude
                longitude = point.longitude
                creationTimestamp = System.currentTimeMillis()
            }
            responseHandler.handleSuccess(weather)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }


    override suspend fun getForecastWeather(point: MapPoint): Resource<List<DailyWeatherData>> {
        return try {
            val forecast = api.getForecastWeather(
                point.latitude.toString(),
                point.longitude.toString(),
                context.getCurrentLanguage()
            ).await().dailyForecastData.list
            forecast.apply {
                val timestamp = System.currentTimeMillis()
                forEach {
                    it.apply {
                        latitude = point.latitude
                        longitude = point.longitude
                        creationTimestamp = timestamp
                    }
                }
            }
            responseHandler.handleSuccess(forecast)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}