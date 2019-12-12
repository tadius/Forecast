package com.tadiuzzz.forecast.feature.current.usecase

import com.tadiuzzz.forecast.data.source.WeatherRepository
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(cityName: String) = weatherRepository.getCurrentWeather(cityName)
}