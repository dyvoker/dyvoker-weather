package com.dyvoker.weather.core.repository

import com.dyvoker.weather.core.data.MapPoint

interface GlobalRepository {
    suspend fun getCities(): Map<String, MapPoint>
    suspend fun addCity(cityName: String, point: MapPoint)
    suspend fun saveCities()
}