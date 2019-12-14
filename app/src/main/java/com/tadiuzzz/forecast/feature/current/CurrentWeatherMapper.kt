package com.tadiuzzz.forecast.feature.current

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.data.WeatherResponse

class CurrentWeatherMapper {
    companion object {
        fun remapToCurrentWeather(
            context: Context,
            weatherResponse: WeatherResponse
        ): CurrentWeather {

            val weatherState = weatherResponse.weatherStates[0]

            val city = SpannableStringBuilder(weatherResponse.city.name)

            val temp = SpannableStringBuilder()
                .append(weatherState.main.temp.toInt().toString())
                .append(context.getString(R.string.degree))

            val windSpeed: String =
                if (weatherState.wind.speed != null)
                    weatherState.wind.speed.toInt().toString()
                else
                    "0"

            val windDirection: String = convertWindDirectionToString(context, weatherState.wind.deg)

            val wind =
                SpannableStringBuilder()
                    .bold { append(windSpeed) }
                    .append(" ")
                    .append(context.getString(R.string.wind_speed_metric))
                    .append(", ")
                    .append(windDirection)

            val humidity = SpannableStringBuilder()
                .bold { append(weatherState.main.humidity.toString()) }
                .bold { append(context.getString(R.string.percent)) }

            val pressure =
                SpannableStringBuilder()
                    .bold { append(convertPressure(context, weatherState.main.pressure)) }
                    .append(" ")
                    .append(context.getString(R.string.pressure_metric))

            val description = SpannableStringBuilder()
                .append(weatherState.weather[0].description.capitalize())

            val rainfallLevel = when {
                weatherState.rain != null -> SpannableStringBuilder()
                    .bold { append(weatherState.rain.level.toString()) }
                    .append(" ")
                    .append(context.getString(R.string.rainfall_level))
                weatherState.snow != null -> SpannableStringBuilder()
                    .bold { append(weatherState.snow.level.toString()) }
                    .append(" ")
                    .append(context.getString(R.string.rainfall_level))
                else -> SpannableStringBuilder()
                    .bold { append("0") }
                    .append(" ")
                    .append(context.getString(R.string.rainfall_level))
            }


            return CurrentWeather(city, temp, wind, humidity, pressure, description, rainfallLevel)
        }

        private fun convertWindDirectionToString(context: Context, windDirection: Int?): String {
            if (windDirection == null) return context.getString(R.string.wind_direction_none)
            return when (windDirection) {
                in 0..22 -> context.getString(R.string.wind_direction_n)
                in 23..67 -> context.getString(R.string.wind_direction_ne)
                in 68..112 -> context.getString(R.string.wind_direction_e)
                in 113..157 -> context.getString(R.string.wind_direction_se)
                in 158..202 -> context.getString(R.string.wind_direction_s)
                in 203..247 -> context.getString(R.string.wind_direction_sw)
                in 248..292 -> context.getString(R.string.wind_direction_w)
                in 293..337 -> context.getString(R.string.wind_direction_nw)
                else -> context.getString(R.string.wind_direction_n)
            }
        }

        private fun convertPressure(context: Context, pressure: Int?): String {
            if (pressure == null) return context.getString(R.string.unknown_value)
            return (pressure.toDouble() / 1.333).toInt().toString()
        }
    }
}