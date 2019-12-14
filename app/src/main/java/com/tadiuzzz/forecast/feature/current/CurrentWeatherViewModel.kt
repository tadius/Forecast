package com.tadiuzzz.forecast.feature.current

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.feature.current.usecase.GetCurrentWeather
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentWeather,
    private val context: Context,
    private val infoHandler: InfoHandler
) : ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val currentWeather = MutableLiveData<CurrentWeather>()

    fun getCurrentWeather(): LiveData<CurrentWeather> {
        return currentWeather
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
