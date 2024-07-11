package com.angga.pokedex.presentation

import androidx.paging.PagingData
import com.angga.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class PokemonListState(
    val pokemonList : Flow<PagingData<Pokemon>> = flow { PagingData.empty<Pokemon>() }
)
