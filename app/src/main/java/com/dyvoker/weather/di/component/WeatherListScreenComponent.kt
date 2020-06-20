package com.dyvoker.weather.di.component

import com.dyvoker.weather.core.repository.WeatherRepository
import com.dyvoker.weather.weather.list.WeatherListContract
import com.dyvoker.weather.weather.list.WeatherListFragment
import com.dyvoker.weather.weather.list.WeatherListPresenter
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention
annotation class WeatherListScreenScope

@Module
class WeatherListScreenModule {

    @WeatherListScreenScope
    @Provides
    fun providePresenter(repository: WeatherRepository): WeatherListContract.Presenter =
        WeatherListPresenter(repository)
}

@WeatherListScreenScope
@Component(dependencies = [AppComponent::class], modules = [WeatherListScreenModule::class])
interface WeatherListScreenComponent {

    fun inject(fragment: WeatherListFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): WeatherListScreenComponent
    }
}