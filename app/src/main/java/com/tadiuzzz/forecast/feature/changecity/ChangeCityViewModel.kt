package com.tadiuzzz.forecast.feature.changecity

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.feature.InfoHandler
import com.tadiuzzz.forecast.feature.PermissionManager
import com.tadiuzzz.forecast.feature.SharedPreferenceManager
import com.tadiuzzz.forecast.feature.SingleLiveEvent
import com.tadiuzzz.forecast.feature.changecity.CityItemMapper.Companion.remapListToCityItem
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

    val cityItems = MutableLiveData<List<CityItem>>()

    val isLoadingVisible = ObservableBoolean(false)

    val enteredCity = ObservableField<String>("")

    fun findCities() {
        isLoadingVisible.set(true)
        viewModelScope.launch {
            val cityResponse = async { findCity(enteredCity.get()!!.trim()) }.await()

            withContext(Dispatchers.Main) {
                isLoadingVisible.set(false)
                if (cityResponse.isSuccessful) {
                    if (cityResponse.body() != null) {
                        cityItems.value = remapListToCityItem(cityResponse.body()!!.cities)
                        if (cityItems.value.isNullOrEmpty()) infoHandler.emitErrorMessage("Не найдено")
                    } else
                        infoHandler.emitErrorMessage(context.getString(R.string.error_empty_city_response_body))
                } else {
                    infoHandler.emitErrorMessage(cityResponse.message())
                }
            }
        }
    }
}
