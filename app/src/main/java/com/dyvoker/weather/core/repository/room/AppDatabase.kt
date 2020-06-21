package com.dyvoker.weather.core.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.DailyWeatherData

@Database(entities = [DailyWeatherData::class, CurrentWeatherData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentWeatherDataDao(): CurrentWeatherDataDao
    abstract fun dailyWeatherDataDao(): DailyWeatherDataDao
}