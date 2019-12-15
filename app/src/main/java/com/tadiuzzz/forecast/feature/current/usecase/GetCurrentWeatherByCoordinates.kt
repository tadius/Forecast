package com.tadiuzzz.forecast.feature.current.usecase

import com.tadiuzzz.forecast.data.source.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherByCoordinates @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double, units: String) = weatherRepository.getCurrentWeatherByCoordinations(lat, lon, units)
}