package com.dyvoker.weather.core.repository

import com.dyvoker.weather.core.data.MapPoint

interface CityRepository {
    suspend fun getCities(): Map<String, MapPoint>
    suspend fun addCity(cityName: String, point: MapPoint)
    suspend fun removeCity(cityName: String)
    suspend fun saveCities()
}