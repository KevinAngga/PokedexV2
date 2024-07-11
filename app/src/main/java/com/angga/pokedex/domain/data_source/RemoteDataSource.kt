package com.angga.pokedex.domain.data_source

import com.angga.pokedex.data.remote.dto.PokemonDto
import com.angga.pokedex.data.remote.dto.PokemonListDto
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result

interface RemoteDataSource {
    suspend fun getPokemon(limit : Int, offset : Int): Result<List<PokemonListDto>, DataError.Network>
    suspend fun getPokemonDetail(pokemonName: String): Result<PokemonDto, DataError.Network>
}