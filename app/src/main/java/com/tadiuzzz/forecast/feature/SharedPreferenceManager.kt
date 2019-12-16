package com.tadiuzzz.forecast.feature

import android.content.SharedPreferences
import com.tadiuzzz.forecast.di.scope.AppScope
import javax.inject.Inject

@AppScope
class SharedPreferenceManager @Inject constructor(private val sharedPreferences: SharedPreferences){

    companion object {
        val PREF_CITY_ID = "pref_city_id"
        val PREF_UNITS = "pref_units"
        val PREF_IS_METRIC_UNITS = "pref_is_metric_units"
    }
    fun putIntValue(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getIntValue(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun putBooleanValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(key: String, default: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    fun putStringValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringValue(key: String, default: String = ""): String? {
        return sharedPreferences.getString(key, default)
    }

    fun addPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)

    }

    fun removePreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }
}