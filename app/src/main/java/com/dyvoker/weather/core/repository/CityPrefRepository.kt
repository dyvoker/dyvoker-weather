package com.dyvoker.weather.core.repository

import android.content.Context
import com.dyvoker.weather.common.editAwait
import com.dyvoker.weather.core.data.MapPoint

class CityPrefRepository(
    context: Context
) : CityRepository {

    private val prefs = context
        .getSharedPreferences("global_prefs", Context.MODE_PRIVATE)
    private val cities =
        prefs.getStringSet(citiesKey, citiesDefaultStrings)!!.associateByTo(
            mutableMapOf(),
            {
                it.substringBefore('|')
            }, {
                it.split('|').run {
                    MapPoint(this[1].toDouble(), this[2].toDouble())
                }
            })

    override suspend fun getCities(): Map<String, MapPoint> = cities

    override suspend fun addCity(cityName: String, point: MapPoint) {
        cities[cityName] = point
        saveCities()
    }

    override suspend fun removeCity(cityName: String) {
        cities.remove(cityName)
        saveCities()
    }

    override suspend fun saveCities() {
        prefs.editAwait(true) {
            putStringSet(
                citiesKey,
                cities.map {
                    "${it.key}|${it.value.latitude}|${it.value.longitude}"
                }.toSet()
            )
        }
    }

    companion object {
        const val citiesKey = "cities_key"
        private val citiesDefault = mutableMapOf(
            "Санкт-Петербург" to MapPoint(59.950015, 30.316599),
            "Москва" to MapPoint(55.753913, 37.620836)
        )

        val citiesDefaultStrings = citiesDefault.map {
            "${it.key}|${it.value.latitude}|${it.value.longitude}"
        }.toSet()
    }
}