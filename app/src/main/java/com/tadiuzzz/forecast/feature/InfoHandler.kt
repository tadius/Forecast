package com.tadiuzzz.forecast.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tadiuzzz.forecast.di.scope.AppScope
import javax.inject.Inject

@AppScope
class InfoHandler @Inject constructor() {
    val infoMessageEvent = SingleLiveEvent<String>()
    val errorMessageEvent = SingleLiveEvent<String>()
    val isLoadingVisible = MutableLiveData<Boolean>()

    fun getIsLoadingVisible() : LiveData<Boolean> {
        return isLoadingVisible
    }

    fun emitInfoMessage(message: String) {
        infoMessageEvent.value = message
    }
    fun emitErrorMessage(message: String) {
        errorMessageEvent.value = message
    }
    fun showLoading(flag: Boolean) {
        isLoadingVisible.value = flag
    }
}