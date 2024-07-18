package com.angga.pokedex.presentation.team

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonTeamsViewModel(
    private val pokemonTeamRepository: PokemonTeamRepository
) : ViewModel() {
    var state by mutableStateOf(PokemonTeamState())
        private set

    init {
        getPokemonTeams()
    }


    private fun getPokemonTeams() {
        viewModelScope.launch {
            pokemonTeamRepository.getAllPokemonTeam().collectLatest {
                state = state.copy(
                    teams = it
                )
            }
        }
    }

}