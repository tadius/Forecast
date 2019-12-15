package com.tadiuzzz.forecast.data.source

import com.tadiuzzz.forecast.data.WeatherResponse
import com.tadiuzzz.forecast.di.scope.AppScope
import retrofit2.Response
import javax.inject.Inject

@AppScope
class WeatherRepository @Inject constructor(val weatherApi: WeatherApi) {

    suspend fun getCurrentWeatherByCityId(city: String, units: String): Response<WeatherResponse> {
        return weatherApi.getCurrentWeatherByCityId(city, units)
    }
    suspend fun getCurrentWeatherByCoordinations(lat: Double, lon: Double, units: String): Response<WeatherResponse> {
        return weatherApi.getCurrentWeatherByCoordinations(lat, lon, units)
    }
}