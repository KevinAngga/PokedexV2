package com.angga.pokedex.presentation.detail

sealed interface PokemonDetailAction {
    data object OnAddTeamClicked : PokemonDetailAction
}