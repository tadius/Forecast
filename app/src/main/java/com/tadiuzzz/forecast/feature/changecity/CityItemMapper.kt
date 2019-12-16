package com.tadiuzzz.forecast.feature.changecity

import android.text.SpannableStringBuilder
import com.tadiuzzz.forecast.data.CityFound

class CityItemMapper {
    companion object {
        fun remapToCityItem(cityFound: CityFound): CityItem {

            val cityName = SpannableStringBuilder(cityFound.name)
                .append(", ")
                .append(cityFound.country.name)

            val cityId = cityFound.id.toString()

            val coordinates =
                SpannableStringBuilder()
                    .append("[")
                    .append(cityFound.coord.lat.toString())
                    .append(", ")
                    .append(cityFound.coord.lon.toString())
                    .append("]")

            return CityItem(cityName, cityId, coordinates)
        }

        fun remapListToCityItem(citiesFound: List<CityFound>): List<CityItem> {
            val remappedList = ArrayList<CityItem>()
            citiesFound.forEach {
                remappedList.add(remapToCityItem(it))
            }
            return remappedList
        }
    }
}