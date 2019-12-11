package com.tadiuzzz.forecast.data.source

import com.tadiuzzz.forecast.data.WeatherResponse
import com.tadiuzzz.newsapp.di.scope.AppScope
import retrofit2.Response
import javax.inject.Inject

@AppScope
class WeatherRepository @Inject constructor(val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(city: String): WeatherResponse {
        return weatherApi.getCurrentWeather(city)
    }
}