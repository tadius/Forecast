package com.tadiuzzz.forecast.feature

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tadiuzzz.forecast.App
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.databinding.ActivityMainBinding
import com.tadiuzzz.forecast.di.module.ViewModelFactory
import com.tadiuzzz.forecast.feature.PermissionManager.Companion.PERMISSION_ASKED
import com.tadiuzzz.forecast.feature.PermissionManager.Companion.PERMISSION_GRANTED
import com.tadiuzzz.forecast.feature.PermissionManager.Companion.PERMISSION_REJECTED
import com.tadiuzzz.forecast.feature.current.CurrentWeatherFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_CODE = 1337

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = mainViewModel
        binding.lifecycleOwner = this

        mainViewModel.infoHandler.infoMessageEvent.observe(this, Observer {
            showInfoMessage(it)
        })

        mainViewModel.infoHandler.errorMessageEvent.observe(this, Observer {
            showErrorMessage(it)
        })

        mainViewModel.permissionManager.requestLocationPermissionEvent.observe(this, Observer {
            requestPermission()
        })

        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.main_container, CurrentWeatherFragment(), "TAG")
            .disallowAddToBackStack()
            .commit();

    }

    private fun requestPermission() {
        if (!checkPermission()) {
            mainViewModel.permissionManager.locationPermissionGranted.set(PERMISSION_ASKED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                for (i in permissions.indices) {
                    val permission = permissions[i]
                    val grantResult = grantResults[i]

                    if (permission == Manifest.permission.ACCESS_FINE_LOCATION) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            mainViewModel.permissionManager.locationPermissionGranted.set(
                                PERMISSION_GRANTED
                            )
                        } else {
                            mainViewModel.permissionManager.locationPermissionGranted.set(
                                PERMISSION_REJECTED
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return when (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            PackageManager.PERMISSION_GRANTED -> {
                mainViewModel.permissionManager.locationPermissionGranted.set(PERMISSION_GRANTED)
                true
            }
            else -> {
                mainViewModel.permissionManager.locationPermissionGranted.set(PERMISSION_REJECTED)
                false
            }
        }
    }

    private fun showInfoMessage(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun showErrorMessage(message: String) {
        showInfoMessage(message)
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }
}
