package com.angga.pokedex.presentation.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.angga.pokedex.R
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.presentation.components.PokemonText
import com.angga.pokedex.presentation.ui.theme.Archive
import com.angga.pokedex.presentation.ui.theme.PokedexTheme
import com.angga.pokedex.presentation.ui.theme.TeamBanner


@Composable
fun PokemonTeamScreenRoot(
    navigateToDetailPage: (pokemonId: Int) -> Unit,
) {
    val pokemonTeamsViewModel: PokemonTeamsViewModel = hiltViewModel()
    PokemonTeamScreen(
        state = pokemonTeamsViewModel.state,
        onChangeTeamNameClicked = pokemonTeamsViewModel::onAction,
        navigateToDetailPage = {
            navigateToDetailPage(it)
        }
    )
}


@Composable
fun PokemonTeamScreen(
    state: PokemonTeamState,
    onChangeTeamNameClicked: (PokemonTeamAction) -> Unit,
    navigateToDetailPage: (pokemonId: Int) -> Unit,
) {
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        ChangeTeamNameDialog(
            dialogTitle = stringResource(id = R.string.input_team_name),
            onDismiss = { showSheet = false },
            state = state.textFieldState,
            onButtonClick = {
                onChangeTeamNameClicked(PokemonTeamAction.OnChangeTeamNameClicked)
                showSheet = false
            }
        )
    }

    if (state.teams.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.prof_oak),
                modifier = Modifier.size(200.dp),
                contentDescription = "prof_oak")

            Spacer(modifier = Modifier.height(16.dp))

            PokemonText(
                text = stringResource(R.string.empty_team),
                fontSize = 16.sp,
                fontFamily = Archive
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(40.dp)
                    .background(TeamBanner),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PokemonText(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    text = state.teamName,
                    fontFamily = Archive,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            showSheet = true
                        },
                    contentDescription = null,
                    tint = Color.White
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = state.teams,
                ) { index, pokemon ->
                    PokemonTeamItem(
                        index = index,
                        pokemonTeam = pokemon,
                        onClick = { navigateToDetailPage(pokemon.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PokemonTeamScreenPreview() {
    PokedexTheme {
        PokemonTeamScreen(
            state = PokemonTeamState(
                teams = listOf(
                    PokemonTeam(
                        id = 1,
                        name = "bulbasaur",
                        localPath = "",
                        type1 = "grass",
                        type2 = "poison"
                    )
                )
            ),
            onChangeTeamNameClicked = {},
            navigateToDetailPage = {}
        )
    }
}