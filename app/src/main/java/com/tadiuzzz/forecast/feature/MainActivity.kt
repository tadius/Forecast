package com.tadiuzzz.forecast.feature

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tadiuzzz.forecast.App
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.databinding.ActivityMainBinding
import com.tadiuzzz.forecast.di.module.ViewModelFactory
import com.tadiuzzz.forecast.feature.current.CurrentWeatherFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = mainViewModel
        binding.lifecycleOwner = this

        mainViewModel.infoHandler.infoMessageEvent.observe(this, Observer {
            showInfoMessage(it)
        })

        mainViewModel.infoHandler.infoMessageEvent.observe(this, Observer {
            showErrorMessage(it)
        })

        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.main_container, CurrentWeatherFragment(), "TAG")
            .disallowAddToBackStack()
            .commit();

    }

    private fun showInfoMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage(message: String) {
        showInfoMessage(message)
    }
}
