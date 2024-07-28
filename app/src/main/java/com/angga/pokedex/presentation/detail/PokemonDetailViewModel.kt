package com.angga.pokedex.presentation.detail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.angga.pokedex.R
import com.angga.pokedex.domain.model.PokemonCharacteristic
import com.angga.pokedex.domain.model.PokemonDesc
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import com.angga.pokedex.presentation.bottom_nav.Destinations
import com.angga.pokedex.presentation.ui.UiText
import com.angga.pokedex.presentation.widget.PokemonWidgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context,
    private val pokemonRepository: PokemonRepository,
    private val pokemonTeamRepository: PokemonTeamRepository
) : ViewModel() {

    private val pokemonWidgetRepository = PokemonWidgetRepository.get(context)

    var state by mutableStateOf(PokemonDetailState())
        private set

    private val _pokemonId = MutableStateFlow(savedStateHandle.toRoute<Destinations.Detail>().pokemonId)
    val pokemonId = _pokemonId.asStateFlow()

    private val _fromTeamPage = MutableStateFlow(savedStateHandle.toRoute<Destinations.Detail>().fromTeamPage)
    val fromTeamPage = _fromTeamPage.asStateFlow()

    private val eventChannel = Channel<PokemonDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getPokemonDetail()
        getContent()
    }

    fun onAction(action: PokemonDetailAction) {
        when(action) {
            PokemonDetailAction.OnAddTeamClicked -> {
                if (fromTeamPage.value) {
                    deletePokemonFromTeam()
                } else {
                    addPokemonToTeam()
                }
            }
        }
    }

    private fun addPokemonToTeam() {
        viewModelScope.launch {
            when(val insertToTeams = pokemonTeamRepository.inputPokemonToTeam(state.pokemon)) {
                is Result.Failed -> {
                    if (insertToTeams.error == DataError.Local.MAX_POKEMON_TEAM) {
                        eventChannel.send(
                            PokemonDetailEvent.Error(UiText.StringResource(R.string.team_already_full))
                        )
                    } else {
                       eventChannel.send(
                           PokemonDetailEvent.Error(UiText.StringResource(R.string.device_storage_full))
                       )
                    }
                }

                is Result.Success -> {
                    eventChannel.send(
                        PokemonDetailEvent.Error(UiText.StringResource(
                            id = R.string.success_add,
                            arrayOf(insertToTeams.data.replaceFirstChar { it.uppercase() })
                        ))
                    )
                    pokemonWidgetRepository.reload()
                }
            }
        }
    }

    private fun deletePokemonFromTeam() {
        viewModelScope.launch {
            when(val deletePokemon = pokemonTeamRepository.deletePokemonFromTeam(state.pokemon)) {
                is Result.Failed -> {}
                is Result.Success -> {
                    eventChannel.send(
                        PokemonDetailEvent.Error(UiText.StringResource(
                            id = R.string.success_remove,
                            arrayOf(deletePokemon.data.replaceFirstChar { it.uppercase() })
                        ))
                    )
                    pokemonWidgetRepository.reload()
                }
            }
        }
    }


    private fun getPokemonDetail() {
        viewModelScope.launch {
            val pokemon = pokemonRepository.getPokemonDetail(pokemonId.value)
            state = state.copy(
                pokemon = pokemon
            )
        }
    }

    private fun getContent() {
        viewModelScope.launch {
            val responseContent = async { pokemonRepository.getPokemonContentDto(pokemonId.value) }
            val responseCharacteristic = async { pokemonRepository.getPokemonCharacteristic(pokemonId.value) }

            val (content, characteristic) = awaitAll(responseContent, responseCharacteristic)

            when {
                content is Result.Success && characteristic is Result.Success -> {
                    val data1 = content.data as PokemonDesc
                    val data2 = characteristic.data as PokemonCharacteristic

                    state = state.copy(
                        pokemonDesc = data1.desc,
                        habitat = data1.habitat,
                        characteristic = data2.characteristic
                    )
                } else -> {}
            }
        }
    }


}