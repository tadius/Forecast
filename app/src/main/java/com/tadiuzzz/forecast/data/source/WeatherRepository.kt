package com.tadiuzzz.forecast.data.source

import com.tadiuzzz.forecast.data.WeatherResponse
import com.tadiuzzz.forecast.di.scope.AppScope
import javax.inject.Inject

@AppScope
class WeatherRepository @Inject constructor(val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(city: String): WeatherResponse {
        return weatherApi.getCurrentWeather(city)
    }
}