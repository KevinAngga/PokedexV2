package com.angga.pokedex.presentation.team

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import com.angga.pokedex.presentation.utils.DEFAULT_TEAM_NAME
import com.angga.pokedex.presentation.utils.TEAM_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonTeamsViewModel @Inject constructor(
    private val pokemonTeamRepository: PokemonTeamRepository,
    private val editor: Editor,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    var state by mutableStateOf(PokemonTeamState())
        private set

    init {
        getTeamName()
        getPokemonTeams()

        viewModelScope.launch {
            snapshotFlow { state.textFieldState.text }
        }
    }

    fun onAction(pokemonTeamAction: PokemonTeamAction) {
        when (pokemonTeamAction) {
            PokemonTeamAction.OnChangeTeamNameClicked -> {
                saveTeamName(state.textFieldState.text.toString().trim())
            }
        }
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

    private fun saveTeamName(teamName : String) {
        editor.putString(TEAM_NAME, teamName).apply()
        getTeamName()
    }

    private fun getTeamName() {
        val teamName = sharedPreferences.getString(TEAM_NAME, DEFAULT_TEAM_NAME) ?: DEFAULT_TEAM_NAME
        state = state.copy(
            teamName = teamName
        )
    }
}