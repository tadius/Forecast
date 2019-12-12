package com.tadiuzzz.forecast.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tadiuzzz.forecast.App
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.feature.current.CurrentWeatherFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)

        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.main_container, CurrentWeatherFragment(), "TAG")
            .disallowAddToBackStack()
            .commit();

    }
}
