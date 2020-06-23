package com.dyvoker.weather.di.component

import android.content.Context
import com.dyvoker.weather.core.AppConfig
import com.dyvoker.weather.core.repository.CityRepository
import com.dyvoker.weather.core.repository.WeatherRepository
import com.dyvoker.weather.map.WeatherMapActivity
import com.dyvoker.weather.map.WeatherMapContract
import com.dyvoker.weather.map.WeatherMapPresenter
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention
annotation class WeatherMapScreenScope

@Module
class WeatherMapScreenModule {

    @WeatherMapScreenScope
    @Provides
    fun providePresenter(
        context: Context,
        appConfig: AppConfig,
        repository: WeatherRepository,
        cityRepository: CityRepository
    ): WeatherMapContract.Presenter =
        WeatherMapPresenter(context, appConfig, repository, cityRepository)
}

@WeatherMapScreenScope
@Component(dependencies = [AppComponent::class], modules = [WeatherMapScreenModule::class])
interface WeatherMapScreenComponent {

    fun inject(activity: WeatherMapActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): WeatherMapScreenComponent
    }
}