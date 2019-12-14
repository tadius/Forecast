package com.tadiuzzz.forecast.feature.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tadiuzzz.forecast.data.WeatherResponse
import com.tadiuzzz.forecast.feature.current.usecase.GetCurrentWeather
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentWeather,
    private val infoHandler: InfoHandler) : ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val currentWeather = MutableLiveData<WeatherResponse>()

    fun getCurrentWeather(): LiveData<WeatherResponse> {
        return currentWeather
    }

    fun updateCurrentWeather(cityName: String) {
        infoHandler.showLoading(true)
        viewModelScope.launch {
            val weatherResponse = async { getCurrentWeather(cityName) }.await()

            withContext(Dispatchers.Main) {
                infoHandler.showLoading(false)
                infoHandler.emitInfoMessage("Погода получена")
                if (weatherResponse.isSuccessful) {
                    currentWeather.value = weatherResponse.body()
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
