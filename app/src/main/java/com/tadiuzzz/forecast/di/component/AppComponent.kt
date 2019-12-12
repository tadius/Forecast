package com.tadiuzzz.forecast.di.component

import android.content.Context
import com.tadiuzzz.forecast.di.module.ViewModelModule
import com.tadiuzzz.forecast.di.module.NetworkModule
import com.tadiuzzz.forecast.di.scope.AppScope
import com.tadiuzzz.forecast.feature.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
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