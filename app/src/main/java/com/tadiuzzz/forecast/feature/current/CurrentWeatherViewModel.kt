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
import com.tadiuzzz.forecast.feature.current.usecase.GetCurrentWeather
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentWeather,
    private val context: Context,
    private val infoHandler: InfoHandler,
    private val permissionManager: PermissionManager
) : ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val currentWeather = MutableLiveData<CurrentWeather>()
    val isMetricUnits = ObservableBoolean(true)

    fun getCurrentWeather(): LiveData<CurrentWeather> {
        return currentWeather
    }

    fun onTempSwitcherClick() {
        isMetricUnits.set(!isMetricUnits.get())
        updateCurrentWeather("Ростов-на-Дону")
        //TODO: update units value in SharedPreference and update current weather
    }

    fun onMyLocationClick() {
        permissionManager.requestLocationPermissionEvent.call()

        if (permissionManager.locationPermissionGranted.get() == PERMISSION_GRANTED) {
            updateWeatherForCurrentLocation()
            return
        }
        permissionManager.locationPermissionGranted.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
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
            .addOnSuccessListener { location : Location? ->
                if (location != null)
                    infoHandler.emitInfoMessage("${location.latitude.toString()} - ${location.longitude.toString()}")
            }
    }

    fun updateCurrentWeather(cityName: String) {
        infoHandler.showLoading(true)
        viewModelScope.launch {
            val weatherResponse = async { getCurrentWeather(cityName, units = "metric") }.await()

            withContext(Dispatchers.Main) {
                infoHandler.showLoading(false)
                if (weatherResponse.isSuccessful) {
                    if (weatherResponse.body() != null)
                        currentWeather.value = CurrentWeatherMapper.remapToCurrentWeather(context, weatherResponse.body()!!)
                    else
                        infoHandler.emitErrorMessage(context.getString(R.string.error_empty_response_body))
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
