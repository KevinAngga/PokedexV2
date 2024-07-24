package com.angga.pokedex.presentation.detail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.angga.pokedex.domain.model.PokemonCharacteristic
import com.angga.pokedex.domain.model.PokemonDesc
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import com.angga.pokedex.domain.utils.Result
import com.angga.pokedex.presentation.bottom_nav.Destinations
import com.angga.pokedex.presentation.widget.PokemonWidgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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

    init {
        getPokemonDetail()
        getContent()
    }

    fun onAction(action: PokemonDetailAction) {
        when(action) {
            PokemonDetailAction.OnAddTeamClicked -> {
                addPokemonToTeam()
            }
        }
    }

    private fun addPokemonToTeam() {
        viewModelScope.launch {
            pokemonTeamRepository.inputPokemonToTeam(state.pokemon)
            pokemonWidgetRepository.reload()
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
                } else -> {
                    Timber.e("== error lee")
                }
            }
        }
    }


}