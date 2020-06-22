package com.dyvoker.weather.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.weather.list.WeatherListFragment

class CitiesAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var cities: Map<String, MapPoint> = mapOf()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = cities.size

    override fun createFragment(position: Int): Fragment {
        val coordinates = cities.values.elementAt(position)
        val fragment = WeatherListFragment()
        val bundle = Bundle().apply {
            putDouble(WeatherListFragment.latitudeKey, coordinates.latitude)
            putDouble(WeatherListFragment.longitudeKey, coordinates.longitude)
        }
        fragment.arguments = bundle
        return fragment
    }
}