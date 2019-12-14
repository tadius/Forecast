package com.tadiuzzz.forecast.data.source

import com.tadiuzzz.forecast.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

   @GET("weather")
   suspend fun getCurrentWeather(@Query("q") cityName: String): Response<WeatherResponse>
}