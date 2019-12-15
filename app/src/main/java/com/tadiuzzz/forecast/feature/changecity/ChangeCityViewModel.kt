package com.tadiuzzz.forecast.feature.changecity

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.data.CityItem
import com.tadiuzzz.forecast.data.CityResponse
import com.tadiuzzz.forecast.feature.InfoHandler
import com.tadiuzzz.forecast.feature.PermissionManager
import com.tadiuzzz.forecast.feature.SharedPreferenceManager
import com.tadiuzzz.forecast.feature.changecity.usecase.FindCity
import kotlinx.coroutines.*
import javax.inject.Inject

class ChangeCityViewModel @Inject constructor(
    private val findCity: FindCity,
    private val context: Context,
    private val infoHandler: InfoHandler,
    private val permissionManager: PermissionManager,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    val foundCities = MutableLiveData<List<CityItem>>()

    val enteredCity = ObservableField<String>("")

    fun findCities() {
        infoHandler.showLoading(true)
        viewModelScope.launch {
            val cityResponse = async { findCity(enteredCity.get()!!.trim()) }.await()

            withContext(Dispatchers.Main) {
                infoHandler.showLoading(false)
                if (cityResponse.isSuccessful) {
                    if (cityResponse.body() != null) {
                        val resp = cityResponse.body()
                        val cit = resp!!.cities
                        foundCities.value = cit
                    } else
                        infoHandler.emitErrorMessage(context.getString(R.string.error_empty_weather_response_body))
                } else {
                    infoHandler.emitErrorMessage(cityResponse.message())
                }
            }
        }
    }
}
