package com.angga.pokedex.di

import androidx.room.Room
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.remote.HttpClientFactory
import com.angga.pokedex.data.remote.data_source.KtorRemoteDataSource
import com.angga.pokedex.data.remote.utils.POKEMON_DATABASE
import com.angga.pokedex.data.repository.PokemonRepositoryImpl
import com.angga.pokedex.domain.data_source.RemoteDataSource
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.presentation.PokemonViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory().build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            PokemonDatabase::class.java,
            POKEMON_DATABASE
        ).build()
    }

    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()
    viewModelOf(::PokemonViewModel)
    single { get<PokemonDatabase>().pokemonDao }
    single { get<PokemonDatabase>().pokemonRemoteKeysDao }
}