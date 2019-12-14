package com.tadiuzzz.forecast.feature.current

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.data.WeatherResponse

class CurrentWeatherMapper {
    companion object {
        fun remapToCurrentWeather(context: Context, weatherResponse: WeatherResponse): CurrentWeather {

            val city = SpannableStringBuilder(weatherResponse.name)

            val temp =
                if (weatherResponse.main.temp != null)
                    SpannableStringBuilder()
                        .bold{append(weatherResponse.main.temp.toInt().toString())}
                        .append(context.getString(R.string.degree))
                else
                    SpannableStringBuilder(context.getString(R.string.unknown_value))

            val windSpeed: String =
                if (weatherResponse.wind.speed != null)
                    weatherResponse.wind.speed.toInt().toString()
                else
                    context.getString(R.string.unknown_value)

            val windDirection: String = convertWindDirectionToString(context, weatherResponse.wind.deg)

            val wind =
                SpannableStringBuilder()
                    .bold{append(windSpeed)}
                    .append(" ")
                    .append(context.getString(R.string.wind_speed_metric))
                    .append(", ")
                    .append(windDirection)

            val humidity =
                if (weatherResponse.main.humidity != null)
                    SpannableStringBuilder()
                        .bold{append(weatherResponse.main.humidity.toString())}
                        .bold{append(context.getString(R.string.percent))}
                else
                    SpannableStringBuilder(context.getString(R.string.unknown_value))

            val pressure: SpannableStringBuilder =
                SpannableStringBuilder()
                    .bold{append(convertPressure(context, weatherResponse.main.pressure))}
                    .append(" ")
                    .append(context.getString(R.string.pressure_metric))

            val description: SpannableStringBuilder =
                if (!weatherResponse.weather.isEmpty())
                    SpannableStringBuilder()
                        .append(weatherResponse.weather.get(0).description)
                else
                    SpannableStringBuilder(context.getString(R.string.unknown_value))


            return CurrentWeather(city, temp, wind, humidity, pressure, description)
        }

        private fun convertWindDirectionToString(context: Context, windDirection: Int?): String {
            if (windDirection == null) return context.getString(R.string.wind_direction_none)
            when (windDirection) {
                in 0..22 -> return context.getString(R.string.wind_direction_n)
                in 23..67 -> return context.getString(R.string.wind_direction_ne)
                in 68..112 -> return context.getString(R.string.wind_direction_e)
                in 113..157 -> return context.getString(R.string.wind_direction_se)
                in 158..202 -> return context.getString(R.string.wind_direction_s)
                in 203..247 -> return context.getString(R.string.wind_direction_sw)
                in 248..292 -> return context.getString(R.string.wind_direction_w)
                in 293..337 -> return context.getString(R.string.wind_direction_nw)
                else -> return context.getString(R.string.wind_direction_n)
            }
        }

        private fun convertPressure(context: Context, pressure: Int?) : String {
            if (pressure == null) return context.getString(R.string.unknown_value)
            return (pressure.toDouble()/1.333).toInt().toString()
        }
    }
}