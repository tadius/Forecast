package com.tadiuzzz.forecast.feature.current

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tadiuzzz.forecast.App
import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.databinding.FragmentCurrentWeatherBinding
import com.tadiuzzz.forecast.di.module.ViewModelFactory
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CurrentWeatherViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[CurrentWeatherViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCurrentWeatherBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_current_weather, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.updateCurrentWeather("Krasnodar") //TODO: change to shared preference call

        return binding.root
    }

}
