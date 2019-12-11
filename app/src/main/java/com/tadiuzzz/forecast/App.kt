package com.tadiuzzz.forecast

import android.app.Application
import com.tadiuzzz.newsapp.di.component.AppComponent
import com.tadiuzzz.newsapp.di.component.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}