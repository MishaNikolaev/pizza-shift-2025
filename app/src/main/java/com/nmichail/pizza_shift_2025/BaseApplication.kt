package com.nmichail.pizza_shift_2025

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

val Context.dataStore by preferencesDataStore(name = "user_prefs")

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
} 