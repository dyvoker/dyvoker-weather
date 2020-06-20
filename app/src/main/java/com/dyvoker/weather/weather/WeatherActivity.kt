package com.dyvoker.weather.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
import com.dyvoker.weather.common.WeatherIconUtils
import com.dyvoker.weather.common.toCelsiusInt
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.databinding.ActivityWeatherBinding
import com.dyvoker.weather.di.component.DaggerWeatherScreenComponent
import com.dyvoker.weather.map.WeatherMapActivity
import com.dyvoker.weather.weather.list.WeatherListFragment
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), CurrentWeatherContract.View {

    private lateinit var binding: ActivityWeatherBinding
    private val cities = mutableMapOf(
        "Санкт-Петербург" to MapPoint(59.950015, 30.316599),
        "Москва" to MapPoint(55.753913, 37.620836)
    )

    @Inject
    lateinit var presenter: CurrentWeatherContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Toolbar.
        with(binding.toolbarWidget.toolbar) {
            inflateMenu(R.menu.menu_main_activity)
            val checkOnMap: View = findViewById(R.id.action_check_on_map)
            checkOnMap.setOnClickListener {
                startActivity(Intent(this@WeatherActivity, WeatherMapActivity::class.java))
            }
        }

        // View pager.
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = cities.size

            override fun createFragment(position: Int): Fragment {
                return WeatherListFragment() // cities[position]
            }
        }
        binding.viewPager.offscreenPageLimit = cities.size // Prevents destroying fragments.

        // Tab layout.
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = cities.keys.elementAt(position)
        }.attach()

        // DI.
        val appComponent = App.appComponent()
        DaggerWeatherScreenComponent.factory().create(appComponent).inject(this)

        presenter.attach(this)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val coordinates = cities.values.elementAt(position)
                presenter.updateWeather(coordinates)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun showWeather(data: CurrentWeatherData) {
        binding.currentIcon.setImageResource(WeatherIconUtils.getResId(data.icon))
        binding.currentTemperature.text = "${data.temperature.toCelsiusInt()}°C"
        binding.currentStatus.text = data.summary
    }
}