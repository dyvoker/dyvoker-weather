package com.dyvoker.weather.core.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dyvoker.weather.core.data.DailyWeatherData

@Dao
interface DailyWeatherDataDao {

    @Query("SELECT * FROM dailyWeatherData WHERE latitude = :latitude AND longitude = :longitude")
    suspend fun getForecastWeather(latitude: Double, longitude: Double): List<DailyWeatherData>

    @Insert
    suspend fun insert(weather: List<DailyWeatherData>)

    @Query("DELETE FROM dailyWeatherData WHERE :currentMillis - creationTimestamp > 3600000")
    suspend fun deleteOld(currentMillis: Long)
}