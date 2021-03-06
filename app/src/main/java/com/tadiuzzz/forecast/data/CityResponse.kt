package com.tadiuzzz.forecast.data

import com.google.gson.annotations.SerializedName

class CityResponse(
    @SerializedName("message") val message : String,
    @SerializedName("cod") val cod : Int,
    @SerializedName("count") val count : Int,
    @SerializedName("list") val cities: List<CityFound>
)

data class CityFound (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("coord") val coord : Coord,
    @SerializedName("main") val main : Main,
    @SerializedName("dt") val dt : Int,
    @SerializedName("wind") val wind : Wind,
    @SerializedName("sys") val country : Country,
    @SerializedName("rain") val rain : Rain,
    @SerializedName("snow") val snow : Snow,
    @SerializedName("clouds") val clouds : Clouds,
    @SerializedName("weather") val weather : List<Weather>
)

data class Country(
    @SerializedName("country") val name: String
)