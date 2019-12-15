package com.tadiuzzz.forecast.feature.current

import android.content.Context
import android.location.Location
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.feature.InfoHandler
import com.tadiuzzz.forecast.feature.PermissionManager
import com.tadiuzzz.forecast.feature.PermissionManager.Companion.PERMISSION_GRANTED
import com.tadiuzzz.forecast.feature.PermissionManager.Companion.PERMISSION_REJECTED
import com.tadiuzzz.forecast.feature.SharedPreferenceManager
import com.tadiuzzz.forecast.feature.SharedPreferenceManager.Companion.PREF_CITY_ID
import com.tadiuzzz.forecast.feature.SharedPreferenceManager.Companion.PREF_IS_METRIC_UNITS
import com.tadiuzzz.forecast.feature.SharedPreferenceManager.Companion.PREF_UNITS
import com.tadiuzzz.forecast.feature.SingleLiveEvent
import com.tadiuzzz.forecast.feature.current.usecase.GetCurrentWeatherByCityId
import com.tadiuzzz.forecast.feature.current.usecase.GetCurrentWeatherByCoordinates
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherByCityId: GetCurrentWeatherByCityId,
    private val getCurrentWeatherByCoordinates: GetCurrentWeatherByCoordinates,
    private val context: Context,
    private val infoHandler: InfoHandler,
    private val permissionManager: PermissionManager,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val currentWeather = MutableLiveData<CurrentWeather>()

    val changeCityEvent = SingleLiveEvent<Boolean>()

    val isMetricUnits: ObservableBoolean =
        ObservableBoolean(sharedPreferenceManager.getBooleanValue(PREF_IS_METRIC_UNITS, true))

    fun getCurrentWeather(): LiveData<CurrentWeather> {
        return currentWeather
    }

    fun onTempSwitcherClick() {
        isMetricUnits.set(!isMetricUnits.get())
        when (isMetricUnits.get()) {
            true -> {
                sharedPreferenceManager.putBooleanValue(PREF_IS_METRIC_UNITS, true)
                sharedPreferenceManager.putStringValue(PREF_UNITS, "metric")
            }
            false -> {
                sharedPreferenceManager.putBooleanValue(PREF_IS_METRIC_UNITS, false)
                sharedPreferenceManager.putStringValue(PREF_UNITS, "imperial")
            }
        }
        updateCurrentWeather()
    }

    fun onChangeCityClick() {
        changeCityEvent.call()
    }

    fun onMyLocationClick() {
        permissionManager.requestLocationPermissionEvent.call()

        if (permissionManager.locationPermissionGranted.get() == PERMISSION_GRANTED) {
            updateWeatherForCurrentLocation()
            return
        }
        permissionManager.locationPermissionGranted.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                when (permissionManager.locationPermissionGranted.get()) {
                    PERMISSION_GRANTED -> updateWeatherForCurrentLocation()
                    PERMISSION_REJECTED -> infoHandler.emitErrorMessage(context.getString(R.string.error_no_location_permission))
                }
            }
        })
    }

    private fun updateWeatherForCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null)
                    getCityByLocation(location.latitude, location.longitude)
            }
    }

    private fun getCityByLocation(lat: Double, lon: Double) {
        val units = sharedPreferenceManager.getStringValue(PREF_UNITS, "metric")

        infoHandler.showLoading(true)
        viewModelScope.launch {
            val weatherResponse =
                async { getCurrentWeatherByCoordinates(lat, lon, units!!) }.await()

            withContext(Dispatchers.Main) {
                infoHandler.showLoading(false)
                if (weatherResponse.isSuccessful) {
                    if (weatherResponse.body() != null) {
                        sharedPreferenceManager.putStringValue(
                            PREF_CITY_ID,
                            weatherResponse.body()!!.city.id.toString()
                        )
                        updateCurrentWeather()
                    } else
                        infoHandler.emitErrorMessage(context.getString(R.string.error_empty_weather_response_body))
                } else {
                    infoHandler.emitErrorMessage(weatherResponse.message())
                }
            }
        }
    }

    fun updateCurrentWeather() {
        val city = sharedPreferenceManager.getStringValue(PREF_CITY_ID)
        val units = sharedPreferenceManager.getStringValue(PREF_UNITS, "metric")

        //if there are no city in preferences try to get weather by current location
        if (city.isNullOrEmpty()) {
            onMyLocationClick()
            return
        }

        infoHandler.showLoading(true)
        viewModelScope.launch {
            val weatherResponse = async { getCurrentWeatherByCityId(city, units!!) }.await()

            withContext(Dispatchers.Main) {
                infoHandler.showLoading(false)
                if (weatherResponse.isSuccessful) {
                    if (weatherResponse.body() != null)
                        currentWeather.value = CurrentWeatherMapper.remapToCurrentWeather(
                            context,
                            weatherResponse.body()!!,
                            isMetricUnits.get()
                        )
                    else
                        infoHandler.emitErrorMessage(context.getString(R.string.error_empty_weather_response_body))
                } else {
                    infoHandler.emitErrorMessage(weatherResponse.message())
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
