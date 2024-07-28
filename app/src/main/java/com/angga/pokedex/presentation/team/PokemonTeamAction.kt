package com.angga.pokedex.presentation.team

sealed interface PokemonTeamAction {
    data object OnChangeTeamNameClicked : PokemonTeamAction
}