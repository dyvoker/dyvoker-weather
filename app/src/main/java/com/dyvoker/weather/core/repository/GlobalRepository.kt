package com.dyvoker.weather.core.repository

import android.content.Context
import androidx.core.content.edit
import com.dyvoker.weather.core.data.MapPoint

class GlobalRepository(
    context: Context
) {
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

    fun getCities(): Map<String, MapPoint> = cities

    fun addCity(cityName: String, point: MapPoint) {
        cities[cityName] = point
        saveCities()
    }

    private fun saveCities() {
        prefs.edit(true) {
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
        val citiesDefault = mutableMapOf(
            "Санкт-Петербург" to MapPoint(59.950015, 30.316599),
            "Москва" to MapPoint(55.753913, 37.620836)
        )

        val citiesDefaultStrings = citiesDefault.map {
            "${it.key}|${it.value.latitude}|${it.value.longitude}"
        }.toSet()
    }
}