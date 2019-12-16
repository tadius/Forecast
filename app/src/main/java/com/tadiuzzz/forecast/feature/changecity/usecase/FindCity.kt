package com.tadiuzzz.forecast.feature.changecity.usecase

import com.tadiuzzz.forecast.data.source.WeatherRepository
import javax.inject.Inject

class FindCity @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(cityName: String) = weatherRepository.findCity(cityName)
}