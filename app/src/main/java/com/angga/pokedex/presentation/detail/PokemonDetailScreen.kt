package com.angga.pokedex.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.angga.pokedex.R
import com.angga.pokedex.presentation.components.PokemonCircularText
import com.angga.pokedex.presentation.components.PokemonDetailShimmer
import com.angga.pokedex.presentation.components.PokemonText
import com.angga.pokedex.presentation.list.calcDominantColor
import com.angga.pokedex.presentation.ui.ObserveAsEvents
import com.angga.pokedex.presentation.ui.theme.Archive
import com.angga.pokedex.presentation.ui.theme.LineColor
import com.angga.pokedex.presentation.ui.theme.NormalType
import com.angga.pokedex.presentation.ui.theme.PokedexTheme
import com.angga.pokedex.presentation.ui.theme.ShadowText
import com.angga.pokedex.presentation.utils.formatNumberWithLeadingZeros
import com.angga.pokedex.presentation.utils.getHeightString
import com.angga.pokedex.presentation.utils.getWeightString

@Composable
fun PokemonDetailScreenRoot() {
    val detailViewModel : PokemonDetailViewModel = hiltViewModel()
    val context = LocalContext.current

    ObserveAsEvents(flow = detailViewModel.events) { pokemonDetailEvent ->
        when (pokemonDetailEvent) {
            is PokemonDetailEvent.Error -> {
                Toast.makeText(
                    context,
                    pokemonDetailEvent.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is PokemonDetailEvent.SuccessAddPokemonToTeam -> {}
            is PokemonDetailEvent.SuccessDeletedPokemonToTeam -> {}
        }
    }

    PokemonDetailScreen(
        state = detailViewModel.state,
        onAddTeamClicked = detailViewModel::onAction
    )
}

@Composable
fun PokemonDetailScreen(
    state: PokemonDetailState,
    onAddTeamClicked: (PokemonDetailAction) -> Unit,
) {
    val defaultDominantColor = NormalType
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                    .clickable {
                        onAddTeamClicked(PokemonDetailAction.OnAddTeamClicked)
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "contentDescription",
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .background(dominantColor)
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Image(
                        painterResource(id = R.drawable.pokeball),
                        modifier = Modifier
                            .size(LocalConfiguration.current.screenWidthDp.dp * 0.5f),
                        contentDescription = "backImage",
                        contentScale = ContentScale.FillWidth,
                    )

                    AsyncImage(
                        modifier = Modifier
                            .size(200.dp)
                            .offset(y = 20.dp, x = (-20).dp),
                        model = state.pokemon.getImageUrl(),
                        contentDescription = "",
                        onSuccess = {
                            calcDominantColor(drawable = it.result.drawable) {
                                dominantColor = it
                            }
                        },
                        onError = {
                            dominantColor = NormalType
                        },
                        contentScale = ContentScale.FillHeight,
                    )
                }

            }

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .weight(2f)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(2f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        PokemonText(
                            text = state.pokemon.name.replaceFirstChar { it.uppercase() },
                            fontFamily = Archive,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row {
                            state.pokemon.types[0]?.let { PokemonCircularText(text = it) }
                            Spacer(modifier = Modifier.width(4.dp))
                            state.pokemon.types[1]?.let { if (it.isNotEmpty()) PokemonCircularText(text = it) }
                        }
                    }

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        PokemonText(
                            text = state.pokemon.id.toString().formatNumberWithLeadingZeros(),
                            fontFamily = Archive,
                            fontSize = 18.sp,
                            color = ShadowText,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(LineColor)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PokemonText(
                            text = state.pokemon.weight.getWeightString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        PokemonText(
                            text = stringResource(
                                id = R.string.weight
                            ),
                            color = ShadowText
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .width(1.dp)
                            .height(42.dp)
                            .background(LineColor)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PokemonText(
                            text = state.pokemon.height.getHeightString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        PokemonText(
                            text = stringResource(
                                id = R.string.height
                            ),
                            color = ShadowText
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(LineColor)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        state.pokemon.abilities[0]?.let {
                            PokemonText(
                                text = it,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        state.pokemon.abilities[1]?.let {
                            PokemonText(
                                text = it,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    PokemonText(
                        text = stringResource(
                            id = R.string.abilities
                        ),
                        color = ShadowText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .background(LineColor)
                            .fillMaxWidth()
                    )
                }


                Spacer(modifier = Modifier.height(12.dp))
                
                if (state.isLoading) {
                    PokemonDetailShimmer()
                } else {
                    PokemonHabitatAndCharacteristic(state = state)
                }
            }
        }
    }
}

@Composable
fun PokemonHabitatAndCharacteristic(state: PokemonDetailState) {
    Column {
        PokemonText(
            text = state.pokemonDesc,
            color = ShadowText
        )

        Spacer(modifier = Modifier.height(16.dp))

        PokemonText(
            text = stringResource(
                id = R.string.habitat
            ),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        PokemonText(
            text = state.habitat,
            color = ShadowText
        )

        Spacer(modifier = Modifier.height(16.dp))

        PokemonText(
            text = stringResource(
                id = R.string.characteristic
            ),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        PokemonText(
            text = state.characteristic,
            color = ShadowText
        )
    }
}

@Preview
@Composable
private fun PokemonDetailScreenPreview() {
    PokedexTheme {
        PokemonDetailScreen(
            state = PokemonDetailState(),
            onAddTeamClicked = {}
        )
    }
}