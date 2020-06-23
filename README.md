# dyvoker-weather
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

This app has production-ready quality.

## Technologies
- MVP
- Kotlin 100% (+ Coroutines)
- AndroidX
- Dagger 2 (Dependent Components)
- Room
- Retrofit2, OkHttp3 (with custom error handling)
- Google Maps
- Permissions
- Gson
- Custom View
- Drawing to bitmap
- ViewPager2 + TabLayout
- Constraint Layout
- RecyclerView (with my own Adapter implementation for single view type)

## Features
- Forecast screen
  - Two default city to show weather for
  - Current and forecast weather for location
  - Caching forecast for one hour
  - Adding and removing of locations to show weather for

- Map screen
  - Current weather by tap on the map
  - Switching of my location detecting between Google Maps and my own implementation through build config (the own implementation is default) (Tests-friendly)
