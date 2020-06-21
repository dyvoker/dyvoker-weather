package com.dyvoker.weather.common

import kotlin.math.roundToInt

fun Float.toCelsiusInt() = this.toCelsiusDouble().roundToInt()

fun Float.toCelsiusDouble() = (this - 32.0f) / 1.8f