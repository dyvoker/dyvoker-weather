package com.dyvoker.weather.common

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


fun Context.getCurrentLanguage(): String = this.getCurrentLocale().language

fun Context.getCurrentLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        resources.configuration.locale
    }
}

/**
 * Coroutine version of SharedPreferences.edit().
 */
suspend fun SharedPreferences.editAwait(
    commit: Boolean = false,
    action: SharedPreferences.Editor.() -> Unit
) = withContext(Dispatchers.Default) {
    this@editAwait.edit(commit, action)
}