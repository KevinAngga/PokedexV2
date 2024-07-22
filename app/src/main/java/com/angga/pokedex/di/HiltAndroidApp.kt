package com.angga.pokedex.di

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltAndroidApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workFactory : HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this,
            workManagerConfiguration
        )
    }
}