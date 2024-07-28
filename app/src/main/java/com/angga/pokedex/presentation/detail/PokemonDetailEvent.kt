package com.angga.pokedex.presentation.detail

import com.angga.pokedex.presentation.ui.UiText

sealed interface PokemonDetailEvent {
    data class Error(val error : UiText) : PokemonDetailEvent
    data class SuccessAddPokemonToTeam(val pokemonName: String) : PokemonDetailEvent
    data class SuccessDeletedPokemonToTeam(val pokemonName: String) : PokemonDetailEvent
}