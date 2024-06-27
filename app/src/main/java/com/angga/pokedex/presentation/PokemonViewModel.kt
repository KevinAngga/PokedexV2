package com.angga.pokedex.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    val string = "asd"

    fun getPokemon() {
        viewModelScope.launch {
            pokemonRepository.getPokemonList()
        }
        println("== jalan")
    }
}