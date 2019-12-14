package com.tadiuzzz.forecast.feature

import androidx.lifecycle.ViewModel
import com.tadiuzzz.forecast.feature.current.InfoHandler
import javax.inject.Inject

class MainViewModel @Inject constructor(val infoHandler: InfoHandler) : ViewModel() {

}