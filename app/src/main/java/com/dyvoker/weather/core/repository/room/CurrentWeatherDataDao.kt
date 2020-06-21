package com.dyvoker.weather.core.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dyvoker.weather.core.data.CurrentWeatherData

@Dao
interface CurrentWeatherDataDao {

    @Query("SELECT * FROM currentWeatherData WHERE latitude = :latitude AND longitude = :longitude LIMIT 1")
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherData?

    @Insert
    suspend fun insert(vararg weather: CurrentWeatherData)

    @Query("DELETE FROM currentWeatherData WHERE :currentMillis - creationTimestamp > 60 * 60 * 1000")
    suspend fun deleteOld(currentMillis: Long)
}

