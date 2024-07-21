package com.angga.pokedex.di

import com.angga.pokedex.data.remote.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokedexNetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient() : HttpClient {
        return HttpClientFactory().build()
    }

}