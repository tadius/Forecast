package com.tadiuzzz.forecast.feature.changecity

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.tadiuzzz.forecast.App

import com.tadiuzzz.forecast.R
import com.tadiuzzz.forecast.databinding.FragmentChangeCityBinding
import com.tadiuzzz.forecast.di.module.ViewModelFactory
import javax.inject.Inject

class ChangeCityFragment : DialogFragment() {

    companion object {
        fun newInstance() = ChangeCityFragment()
    }

    val adapter = CityAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ChangeCityViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[ChangeCityViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentChangeCityBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_change_city, container, false)

        binding.viewmodel = viewModel
        adapter.setOnItemClickListener(object : CityAdapter.Companion.OnItemClickListener{
            override fun onItemClick(item: CityItem) {
                viewModel.onCityChanged(item)
            }

        })
        binding.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.cityItems.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })

        viewModel.closeDialogEvent.observe(viewLifecycleOwner, Observer {
            dismiss()
        })

        return binding.root
    }

}
