package com.dyvoker.weather.di.component

import android.content.Context
import com.dyvoker.weather.common.App
import com.dyvoker.weather.core.repository.WeatherRepository
import com.dyvoker.weather.di.module.AppModule
import com.dyvoker.weather.di.module.RepositoryModule
import com.dyvoker.weather.di.module.RetrofitModule
import dagger.Component
import javax.inject.Singleton

/**
 * Main Dagger component.
 * Here I using Dependent Components instead of Subcomponents,
 * because it gives support for multi-modules.
 * More at https://developer.android.com/training/dependency-injection/dagger-multi-module
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    // Downstream modules for dependent components.
    fun appContext(): Context
    fun weatherRepository(): WeatherRepository

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule, retrofitModule: RetrofitModule): AppComponent
    }
}