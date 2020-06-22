package com.dyvoker.weather.weather

import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.Resource
import com.dyvoker.weather.core.repository.WeatherRepository
import com.dyvoker.weather.core.repository.room.CurrentWeatherDataDao
import com.dyvoker.weather.core.repository.room.DailyWeatherDataDao

class WeatherCacheRepository(
    private val weatherRepository: WeatherRepository,
    private val currentWeatherDataDao: CurrentWeatherDataDao,
    private val dailyWeatherDataDao: DailyWeatherDataDao
) : WeatherRepository {

    override suspend fun getCurrentWeather(point: MapPoint): Resource<CurrentWeatherData> {
        currentWeatherDataDao.deleteOld(System.currentTimeMillis())
        val weather = currentWeatherDataDao.getCurrentWeather(point.latitude, point.longitude)
        return if (weather == null) {
            val result = weatherRepository.getCurrentWeather(point)
            when (result.status) {
                Resource.Status.SUCCESS -> currentWeatherDataDao.insert(result.data!!)
                else -> {} // Do nothing.
            }
            result
        } else {
            Resource(Resource.Status.SUCCESS, weather, null)
        }
    }

    override suspend fun getForecastWeather(point: MapPoint): Resource<List<DailyWeatherData>> {
        dailyWeatherDataDao.deleteOld(System.currentTimeMillis())
        val list = dailyWeatherDataDao.getForecastWeather(point.latitude, point.longitude)
        return if (list.isEmpty()) {
            val result = weatherRepository.getForecastWeather(point)
            when (result.status) {
                Resource.Status.SUCCESS -> dailyWeatherDataDao.insert(result.data!!)
                else -> {} // Do nothing.
            }
            result
        }
        else {
            Resource(Resource.Status.SUCCESS, list, null)
        }
    }
}