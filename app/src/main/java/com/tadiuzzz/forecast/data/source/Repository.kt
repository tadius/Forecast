package com.tadiuzzz.forecast.data.source

import com.tadiuzzz.forecast.data.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(city: String): Response<WeatherResponse> {
        return weatherApi.getCurrentWeather(city)
    }
}