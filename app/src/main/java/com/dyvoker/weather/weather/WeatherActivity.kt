package com.dyvoker.weather.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
import com.dyvoker.weather.common.toCelsiusInt
import com.dyvoker.weather.core.DarkSkyUtils
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.databinding.ActivityWeatherBinding
import com.dyvoker.weather.di.component.DaggerWeatherScreenComponent
import com.dyvoker.weather.map.WeatherMapActivity
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class WeatherActivity : AppCompatActivity(), CurrentWeatherContract.View {

    private lateinit var binding: ActivityWeatherBinding

    @Inject
    lateinit var presenter: CurrentWeatherContract.Presenter

    lateinit var citiesAdapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar.
        with(binding.toolbarWidget.toolbar) {
            inflateMenu(R.menu.menu_main_activity)
            val checkOnMap: View = findViewById(R.id.action_check_on_map)
            checkOnMap.setOnClickListener {
                startActivityForResult(
                    Intent(this@WeatherActivity, WeatherMapActivity::class.java),
                    weatherMapRequestCode
                )
            }
        }

        citiesAdapter = CitiesAdapter(this)
        binding.viewPager.adapter = citiesAdapter
        binding.viewPager.offscreenPageLimit = 3 // Prevents destroying fragments.

        // Tab layout.
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = citiesAdapter.cities.keys.elementAt(position)
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val coordinates = citiesAdapter.cities.values.elementAt(position)
                presenter.updateWeather(coordinates)
            }
        })

        // DI.
        val appComponent = App.appComponent()
        DaggerWeatherScreenComponent.factory().create(appComponent).inject(this)

        presenter.attach(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == weatherMapRequestCode) {
            presenter.weatherMapViewClosed()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showWeather(data: CurrentWeatherData) {
        binding.currentIcon.setImageResource(DarkSkyUtils.getIconId(data.icon))
        binding.currentTemperature.text = "${data.temperature.toCelsiusInt()}°C"
        binding.currentStatus.text = data.summary
    }

    override fun showCitiesTabs(cities: Map<String, MapPoint>) {
        citiesAdapter.cities = cities
    }

    companion object {
        const val weatherMapRequestCode = 35
    }
}