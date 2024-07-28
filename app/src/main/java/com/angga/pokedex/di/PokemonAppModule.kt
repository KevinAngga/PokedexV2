package com.angga.pokedex.di

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.angga.pokedex.data.local.POKEDEX_SHARED_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonAppModule {
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context) : WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences(POKEDEX_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideSharedPreferenceEditor(preferences: SharedPreferences) : SharedPreferences.Editor {
        return preferences.edit()
    }
}