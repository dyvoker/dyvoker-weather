package com.dyvoker.weather.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.databinding.ActivityWeatherBinding
import com.dyvoker.weather.di.component.DaggerWeatherScreenComponent
import com.dyvoker.weather.map.WeatherMapActivity
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class WeatherActivity : AppCompatActivity(), WeatherContract.View {

    private lateinit var binding: ActivityWeatherBinding

    @Inject
    lateinit var presenter: WeatherContract.Presenter

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
                presenter.clickMapButton()
            }
        }

        // DI.
        DaggerWeatherScreenComponent.factory().create(App.appComponent).inject(this)
        presenter.attach(this)

        citiesAdapter = CitiesAdapter(this)
        binding.viewPager.adapter = citiesAdapter
        binding.viewPager.offscreenPageLimit = 3 // Prevents destroying fragments.

        // Tab layout.
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = citiesAdapter.cities.keys.elementAt(position)
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (citiesAdapter.cities.isNotEmpty()) {
                    val coordinates = citiesAdapter.cities.values.elementAt(position)
                    presenter.updateWeather(coordinates)
                }
            }
        })

        binding.removeCity.setOnClickListener {
            val position = binding.tabLayout.selectedTabPosition
            if (position > -1) {
                presenter.removeCityClick(
                    citiesAdapter.getShowedCityNameByPosition(position)
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == weatherMapRequestCode) {
            presenter.weatherMapViewClosed()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showWeather(data: CurrentWeatherData) {
        binding.currentWeather.setCurrentWeatherData(data)
    }

    override fun showCitiesTabs(cities: Map<String, MapPoint>) {
        citiesAdapter.cities = cities
    }

    override fun openWeatherMap() {
        startActivityForResult(
            Intent(this@WeatherActivity, WeatherMapActivity::class.java),
            weatherMapRequestCode
        )
    }

    override fun showLoadingError() {
        Toast.makeText(this, R.string.loading_error, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val weatherMapRequestCode = 35
    }
}