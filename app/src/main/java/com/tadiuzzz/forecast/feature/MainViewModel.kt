package com.tadiuzzz.forecast.feature

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(val infoHandler: InfoHandler, val permissionManager: PermissionManager) : ViewModel() {

}