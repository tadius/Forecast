package com.tadiuzzz.forecast.feature

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.tadiuzzz.forecast.di.scope.AppScope
import javax.inject.Inject

@AppScope
class PermissionManager @Inject constructor() {
    val requestLocationPermissionEvent = SingleLiveEvent<Boolean>()
    val locationPermissionGranted = ObservableInt(0)

    companion object {
        val PERMISSION_GRANTED = 1
        val PERMISSION_REJECTED = 0
        val PERMISSION_ASKED = -1
    }
}