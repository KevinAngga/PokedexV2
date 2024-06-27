package com.angga.pokedex.di

import com.angga.pokedex.data.remote.HttpClientFactory
import com.angga.pokedex.data.remote.data_source.KtorRemoteDataSource
import com.angga.pokedex.data.repository.PokemonRepositoryImpl
import com.angga.pokedex.domain.data_source.RemoteDataSource
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.presentation.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory().build()
    }

    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()
    viewModelOf(::PokemonViewModel)
}