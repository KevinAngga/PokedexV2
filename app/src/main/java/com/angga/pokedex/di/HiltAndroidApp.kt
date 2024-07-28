package com.angga.pokedex.di

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.angga.pokedex.data.local.CsvLoader
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class HiltAndroidApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workFactory : HiltWorkerFactory

    @Inject
    lateinit var csvLoader: CsvLoader

    @Inject
    @ApplicationContext
    lateinit var appCoroutineScope: CoroutineScope

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

        appCoroutineScope.launch {
            csvLoader.loadCsvData()
        }
    }
}