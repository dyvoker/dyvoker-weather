package com.dyvoker.weather.common

/**
 * Base contract for View and Presenter (for MVP).
 */
class BaseContract {
    interface Presenter<in T> {
        fun attach(view: T)
    }

    interface View
}