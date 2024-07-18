package com.angga.pokedex.presentation.team

import com.angga.pokedex.domain.model.PokemonTeam

data class PokemonTeamState(
    val teams : List<PokemonTeam> = emptyList()
)
