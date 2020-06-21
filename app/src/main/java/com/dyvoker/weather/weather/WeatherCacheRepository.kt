package com.dyvoker.weather.weather

import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.repository.WeatherRepository
import com.dyvoker.weather.core.repository.room.CurrentWeatherDataDao
import com.dyvoker.weather.core.repository.room.DailyWeatherDataDao

class WeatherCacheRepository(
    private val weatherRepository: WeatherRepository,
    private val currentWeatherDataDao: CurrentWeatherDataDao,
    private val dailyWeatherDataDao: DailyWeatherDataDao
) : WeatherRepository {

    override suspend fun getCurrentWeather(point: MapPoint): CurrentWeatherData {
        currentWeatherDataDao.deleteOld(System.currentTimeMillis())
        val weather = currentWeatherDataDao.getCurrentWeather(point.latitude, point.longitude)
        return if (weather == null) {
            val result = weatherRepository.getCurrentWeather(point)
            currentWeatherDataDao.insert(result)
            result
        } else {
            weather
        }
    }

    override suspend fun getForecastWeather(point: MapPoint): List<DailyWeatherData> {
        dailyWeatherDataDao.deleteOld(System.currentTimeMillis())
        val list = dailyWeatherDataDao.getForecastWeather(point.latitude, point.longitude)
        return if (list.isEmpty()) {
            val result = weatherRepository.getForecastWeather(point)
            dailyWeatherDataDao.insert(result)
            result
        }
        else {
            list
        }
    }
}