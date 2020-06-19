package com.dyvoker.weather.common.rv

typealias NotifyObserver = () -> Unit

interface RVItemsProvider<I> {

    fun getCount() : Int
    fun get(position: Int): I
    fun setNotifyObserver(observer: NotifyObserver)
}