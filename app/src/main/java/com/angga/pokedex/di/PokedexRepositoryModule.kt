package com.angga.pokedex.di

import android.content.Context
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.repository.PokemonRepositoryImpl
import com.angga.pokedex.data.repository.PokemonTeamRepositoryImpl
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokedexRepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        httpClient: HttpClient,
        database: PokemonDatabase
    ) : PokemonRepository {
        return PokemonRepositoryImpl(
            httpClient = httpClient,
            pokemonDatabase = database
        )
    }

    @Provides
    @Singleton
    fun providePokemonTeamRepository(
        @ApplicationContext context: Context,
        httpClient: HttpClient,
        database: PokemonDatabase
    ) : PokemonTeamRepository {
        return PokemonTeamRepositoryImpl(
            pokemonDatabase = database,
            httpClient = httpClient,
            context =  context
        )
    }
}