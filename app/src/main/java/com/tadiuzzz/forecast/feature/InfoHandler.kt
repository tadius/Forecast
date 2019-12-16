package com.tadiuzzz.forecast.feature

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tadiuzzz.forecast.di.scope.AppScope
import javax.inject.Inject

@AppScope
class InfoHandler @Inject constructor() {
    val infoMessageEvent = SingleLiveEvent<String>()
    val errorMessageEvent = SingleLiveEvent<String>()
    val isLoadingVisible = ObservableBoolean(false)

    fun emitInfoMessage(message: String) {
        infoMessageEvent.value = message
    }
    fun emitErrorMessage(message: String) {
        errorMessageEvent.value = message
    }
    fun showLoading(flag: Boolean) {
        isLoadingVisible.set(flag)
    }
}