package com.angga.pokedex.domain.data_source

import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result

interface RemoteDataSource {
    suspend fun getPokemon(): Result<List<Pokemon>, DataError.Network>
    suspend fun getPokemonDetail(pokemonName: String): Result<Pokemon, DataError.Network>
}