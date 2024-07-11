package com.angga.pokedex.domain.repository

import androidx.paging.PagingData
import com.angga.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow


interface PokemonRepository {
    suspend fun getPokemonList() : Flow<PagingData<Pokemon>>
}