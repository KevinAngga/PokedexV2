package com.angga.pokedex.domain.repository

import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result

interface PokemonRepository {
    suspend fun getPokemonList() : Result<List<Pokemon>, DataError.Network>
}