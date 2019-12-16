package com.tadiuzzz.forecast.feature.current.usecase

import com.tadiuzzz.forecast.data.source.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherByCityId @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(cityName: String, units: String) = weatherRepository.getCurrentWeatherByCityId(cityName, units)
}