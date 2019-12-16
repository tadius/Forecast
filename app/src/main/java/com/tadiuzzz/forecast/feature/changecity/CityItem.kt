package com.tadiuzzz.forecast.feature.changecity

import android.text.SpannableStringBuilder

data class CityItem(
    val cityName: SpannableStringBuilder,
    val cityId: String,
    val coordinates: SpannableStringBuilder
)