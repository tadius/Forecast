package com.tadiuzzz.forecast.data.source

import com.tadiuzzz.forecast.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast")
    suspend fun getCurrentWeatherByCityId(
        @Query("id") cityId: String,
        @Query("units") units: String,
        @Query("lang") lang: String = "ru"
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getCurrentWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("lang") lang: String = "ru"
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getCurrentWeatherByCoordinations(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String = "ru"
    ): Response<WeatherResponse>
}