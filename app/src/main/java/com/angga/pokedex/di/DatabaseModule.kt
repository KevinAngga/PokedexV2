package com.angga.pokedex.di

import androidx.room.Room
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.remote.utils.POKEMON_DATABASE
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PokemonDatabase::class.java,
            POKEMON_DATABASE
        ).build()
    }

    single { get<PokemonDatabase>().pokemonDao }
    single { get<PokemonDatabase>().pokemonRemoteKeysDao }
}