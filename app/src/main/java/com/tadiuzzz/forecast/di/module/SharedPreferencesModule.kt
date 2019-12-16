package com.tadiuzzz.forecast.di.module

import android.content.Context
import android.content.SharedPreferences
import com.tadiuzzz.forecast.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {

    @Provides
    @AppScope
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("DefaultPreferences", Context.MODE_PRIVATE)
    }

}