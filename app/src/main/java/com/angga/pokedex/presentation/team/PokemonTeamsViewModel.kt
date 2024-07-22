package com.angga.pokedex.presentation.team

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.angga.pokedex.data.remote.RemoteImageWorker
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonTeamsViewModel @Inject constructor(
    private val pokemonTeamRepository: PokemonTeamRepository,
    private val workManager: WorkManager
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

//                println("==== url viewModel "+it[0].getSpriteImageUrl())
//                val inputData = Data.Builder()
//                    .putString("IMAGE_URL", it[0].getSpriteImageUrl())
//                    .putString("POKEMON_NAME", it[0].name)
//                    .build()
//
//                val request = OneTimeWorkRequestBuilder<RemoteImageWorker>()
//                    .setInputData(inputData)
//                    .build()
//                workManager.enqueue(request)
            }
        }
    }

    suspend fun getTeams(): Flow<List<PokemonTeam>> {
        return pokemonTeamRepository.getAllPokemonTeam()
    }



}