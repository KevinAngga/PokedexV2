package com.angga.pokedex.presentation.team

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.angga.pokedex.presentation.components.PokemonText


@Composable
fun PokemonTeamScreenRoot() {
    val pokemonTeamsViewModel : PokemonTeamsViewModel = hiltViewModel()
    PokemonTeamScreen(state = pokemonTeamsViewModel.state)
}


@Composable
fun PokemonTeamScreen(
    state : PokemonTeamState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                items = state.teams,
            ) {index, pokemon ->
                PokemonTeamItem(
                    index = index,
                    pokemonTeam = pokemon
                )
            }
        }
    }
}