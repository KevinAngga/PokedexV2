package com.angga.pokedex.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.angga.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    var state by mutableStateOf(PokemonListState())
        private set

    init {
        viewModelScope.launch {
            getPokemon()
        }
    }

    private suspend fun getPokemon() {
        val result = pokemonRepository
            .getPokemonList()
            .cachedIn(viewModelScope)
        state = state.copy(
            pokemonList = result
        )
    }
}