package com.angga.pokedex.di

import android.content.Context
import androidx.room.Room
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.local.dao.PokemonDao
import com.angga.pokedex.data.local.dao.PokemonRemoteKeysDao
import com.angga.pokedex.data.local.dao.PokemonTeamDao
import com.angga.pokedex.data.local.POKEMON_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
annotation class AppCoroutineScope


@Module
@InstallIn(SingletonComponent::class)
object PokedexDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            POKEMON_DATABASE
        ).build()
    }


    @Provides
    fun providesPokemonDao(pokemonDatabase: PokemonDatabase)
            : PokemonDao = pokemonDatabase.pokemonDao

    @Provides
    fun providesPokemonRemoteKeysDao(pokemonDatabase: PokemonDatabase)
            : PokemonRemoteKeysDao = pokemonDatabase.pokemonRemoteKeysDao

    @Provides
    fun providesPokemonTeamDao(pokemonDatabase: PokemonDatabase)
            : PokemonTeamDao = pokemonDatabase.pokemonTeamDao

    @Provides
    @Singleton
    @AppCoroutineScope
    fun providesApplicationCoroutineScope(): CoroutineScope = CoroutineScope(
        Executors.newSingleThreadExecutor().asCoroutineDispatcher(),
    )
}