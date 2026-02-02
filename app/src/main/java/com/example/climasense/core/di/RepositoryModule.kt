package com.example.climasense.core.di

import com.example.climasense.core.repository.WeatherRepository
import com.example.climasense.core.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract  fun  bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository
}