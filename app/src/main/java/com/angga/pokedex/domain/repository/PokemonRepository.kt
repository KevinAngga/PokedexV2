package com.angga.pokedex.domain.repository

import androidx.paging.PagingData
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonCharacteristic
import com.angga.pokedex.domain.model.PokemonDesc
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import kotlinx.coroutines.flow.Flow


interface PokemonRepository {
    suspend fun getPokemonList() : Flow<PagingData<Pokemon>>
    suspend fun getPokemonDetail(pokemonId : Int) : Pokemon
    suspend fun getPokemonContentDto(pokemonId: Int) : Result<PokemonDesc, DataError.Network>
    suspend fun getPokemonCharacteristic(pokemonId: Int) : Result<PokemonCharacteristic, DataError.Network>
}