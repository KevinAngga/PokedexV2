package com.angga.pokedex.di

import androidx.room.Room
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.remote.HttpClientFactory
import com.angga.pokedex.data.remote.utils.POKEMON_DATABASE
import com.angga.pokedex.data.repository.PokemonRepositoryImpl
import com.angga.pokedex.data.repository.PokemonTeamRepositoryImpl
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import com.angga.pokedex.presentation.detail.PokemonDetailViewModel
import com.angga.pokedex.presentation.list.PokemonViewModel
import com.angga.pokedex.presentation.team.PokemonTeamsViewModel
import com.angga.pokedex.presentation.widget.PokemonWidgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
//    single {
//        HttpClientFactory().build()
//    }

//    single {
//        Room.databaseBuilder(
//            androidApplication(),
//            PokemonDatabase::class.java,
//            POKEMON_DATABASE
//        ).build()
//    }

    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()
    singleOf(::PokemonTeamRepositoryImpl).bind<PokemonTeamRepository>()

    viewModelOf(::PokemonViewModel)
    viewModelOf(::PokemonDetailViewModel)
    viewModelOf(::PokemonTeamsViewModel)

    single { CoroutineScope(Dispatchers.IO) }

//    single { get<PokemonDatabase>().pokemonDao }
//    single { get<PokemonDatabase>().pokemonRemoteKeysDao }
//    single { get<PokemonDatabase>().pokemonTeamDao }
    single { PokemonWidgetRepository(get(), get(), get()) }
}