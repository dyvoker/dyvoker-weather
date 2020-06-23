package com.dyvoker.weather.core

import com.dyvoker.weather.BuildConfig

/**
 * Configuration flags for the whole app.
 */
interface AppConfig {

    val useGoogleMapMyLocationButton: Boolean

    class Default : AppConfig {
        override val useGoogleMapMyLocationButton: Boolean get() = BuildConfig.useGoogleMapMyLocationButton
    }
}