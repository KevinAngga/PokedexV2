package com.angga.pokedex.presentation.team

import androidx.compose.foundation.text.input.TextFieldState
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.presentation.utils.DEFAULT_TEAM_NAME

data class PokemonTeamState(
    val teamName : String = DEFAULT_TEAM_NAME,
    val teams : List<PokemonTeam> = emptyList(),
    val textFieldState: TextFieldState = TextFieldState(DEFAULT_TEAM_NAME)
)
