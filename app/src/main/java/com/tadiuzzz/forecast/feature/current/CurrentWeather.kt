package com.tadiuzzz.forecast.feature.current

import android.text.SpannableStringBuilder

data class CurrentWeather (
    val city: SpannableStringBuilder,
    val temp: SpannableStringBuilder,
    val wind: SpannableStringBuilder,
    val humidity: SpannableStringBuilder,
    val pressure: SpannableStringBuilder,
    val description: SpannableStringBuilder,
    val rainfallLevel: SpannableStringBuilder,
    val icon: String
)