package com.tadiuzzz.newsapp.di.component

import android.content.Context
import com.tadiuzzz.forecast.di.module.ViewModelModule
import com.tadiuzzz.forecast.feature.MainActivity
import com.tadiuzzz.newsapp.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = arrayOf(
        NetworkModule::class,
        ViewModelModule::class
    )
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}