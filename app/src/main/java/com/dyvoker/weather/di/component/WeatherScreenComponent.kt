package com.dyvoker.weather.di.component

import com.dyvoker.weather.core.repository.CityRepository
import com.dyvoker.weather.core.repository.WeatherRepository
import com.dyvoker.weather.weather.WeatherActivity
import com.dyvoker.weather.weather.WeatherContract
import com.dyvoker.weather.weather.WeatherPresenter
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention
annotation class WeatherScreenScope

@Module
class WeatherScreenModule {

    @WeatherScreenScope
    @Provides
    fun providePresenter(
        repository: WeatherRepository,
        cityRepository: CityRepository
    ): WeatherContract.Presenter =
        WeatherPresenter(repository, cityRepository)
}

@WeatherScreenScope
@Component(dependencies = [AppComponent::class], modules = [WeatherScreenModule::class])
interface WeatherScreenComponent {

    fun inject(activity: WeatherActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): WeatherScreenComponent
    }
}