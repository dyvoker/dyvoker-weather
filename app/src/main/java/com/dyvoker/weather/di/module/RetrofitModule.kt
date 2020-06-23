package com.dyvoker.weather.di.module

import com.dyvoker.weather.core.DarkSkyApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RetrofitModule(
    private val baseUrl: String
) {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url()
            val i = originalHttpUrl.pathSegments().indexOf("API_KEY")
            if (i >= 0) {
                val url = originalHttpUrl
                    .newBuilder()
                    .setPathSegment(i, "3e7e519ea86c8e3fcf67c0f4870513d7")
                    .build()
                request.url(url)
            }
            chain.proceed(request.build())
        }
        return okHttpBuilder
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        httpClient: OkHttpClient.Builder
    ): Retrofit {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logger)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideDarkSkyApiService(retrofit: Retrofit): DarkSkyApiService =
        retrofit.create(DarkSkyApiService::class.java)
}