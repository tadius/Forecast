package com.tadiuzzz.forecast.feature.current

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.data.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class CurrentWeatherMapper {
    companion object {
        fun remapToCurrentWeather(
            context: Context,
            weatherResponse: WeatherResponse,
            isMetricUnits: Boolean
        ): CurrentWeather {

            val windSpeedUnits = if (isMetricUnits)
                context.getString(R.string.wind_speed_metric)
            else
                context.getString(R.string.wind_speed_imperial)

            val pressureUnits = if (isMetricUnits)
                context.getString(R.string.pressure_metric)
            else
                context.getString(R.string.pressure_imperial)

            val rainfallUnits = if (isMetricUnits)
                context.getString(R.string.rainfall_metric)
            else
                context.getString(R.string.rainfall_imperial)

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
                    .append(windSpeedUnits)
                    .append(", ")
                    .append(windDirection)

            val humidity = SpannableStringBuilder()
                .bold { append(weatherState.main.humidity.toString()) }
                .bold { append(context.getString(R.string.percent)) }

            val pressure =
                SpannableStringBuilder()
                    .bold { append(convertPressure(context, weatherState.main.pressure, isMetricUnits)) }
                    .append(" ")
                    .append(pressureUnits)

            val description = SpannableStringBuilder()
                .append(weatherState.weather[0].description.capitalize())

            val rainfallLevel = when {
                weatherState.rain != null -> SpannableStringBuilder()
                    .bold { append(convertRainfall(weatherState.rain.level, isMetricUnits)) }
                    .append(" ")
                    .append(rainfallUnits)
                weatherState.snow != null -> SpannableStringBuilder()
                    .bold { append(convertRainfall(weatherState.snow.level, isMetricUnits)) }
                    .append(" ")
                    .append(rainfallUnits)
                else -> SpannableStringBuilder()
                    .bold { append("0") }
                    .append(" ")
                    .append(rainfallUnits)
            }

            val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
            val currentDate = SpannableStringBuilder(dateFormat.format(Date()))

            val icon = weatherState.weather[0].icon

            return CurrentWeather(city, temp, wind, humidity, pressure, description, rainfallLevel, currentDate, icon)
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

        private fun convertPressure(context: Context, pressure: Int?, isMetricUnits: Boolean): String {
            if (pressure == null) return context.getString(R.string.unknown_value)
            if (isMetricUnits)
                return (pressure.toDouble() / 1.333).toInt().toString()
            else
                return (((pressure.toDouble() / 33.864) * 100).roundToInt() / 100.00).toString()
        }

        private fun convertRainfall(rainfallLevel: Double?, isMetricUnits: Boolean): String {
            if (rainfallLevel == null) return "0"
            if (isMetricUnits)
                return ((rainfallLevel * 100).roundToInt() / 100.00).toString()
            else
                return (((rainfallLevel/25.4) * 100).roundToInt() / 100.00).toString()
        }
    }
}