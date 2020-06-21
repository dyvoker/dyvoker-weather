package com.dyvoker.weather.map

import com.dyvoker.weather.core.data.MapPoint
import com.google.android.gms.maps.model.LatLng

fun LatLng.toMapPoint() = MapPoint(this.latitude, this.longitude)
fun MapPoint.toLatLng() = LatLng(this.latitude, this.longitude)