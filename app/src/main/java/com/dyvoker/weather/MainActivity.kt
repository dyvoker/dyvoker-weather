package com.dyvoker.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dyvoker.weather.databinding.ActivityMainBinding
import com.dyvoker.weather.list.WeatherListFragment
import com.dyvoker.weather.map.WeatherMapActivity
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Toolbar.
        with (binding.toolbarWidget.toolbar) {
            inflateMenu(R.menu.menu_main_activity)
            val checkOnMap: View = findViewById(R.id.action_check_on_map)
            checkOnMap.setOnClickListener {
                startActivity(Intent(this@MainActivity, WeatherMapActivity::class.java))
            }
        }

        // View pager.
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> WeatherListFragment()
                    1 -> WeatherListFragment()
                    else -> WeatherListFragment()
                }
            }
        }
        binding.viewPager.offscreenPageLimit = 2 // Prevents destroying fragments.

        // Tab layout.
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Санкт-Петербург"
                1 -> "Москва"
                else -> "Томск" // :)
            }
        }.attach()
    }
}