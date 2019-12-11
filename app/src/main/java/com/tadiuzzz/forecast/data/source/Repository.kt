package com.tadiuzzz.forecast.data.source

import com.tadiuzzz.forecast.data.source.WeatherApi
import javax.inject.Inject

class Repository @Inject constructor(val weatherApi: WeatherApi) {
}