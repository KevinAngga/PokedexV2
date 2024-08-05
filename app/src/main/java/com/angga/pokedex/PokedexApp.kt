package com.angga.pokedex

import android.app.Application
import com.angga.pokedex.data.remote.HttpClientFactory
import com.angga.pokedex.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class PokedexApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@PokedexApp)
            modules(
                listOf(appModule)
            )
        }
    }
}