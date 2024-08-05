package com.angga.pokedex.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.angga.pokedex.R
import com.angga.pokedex.presentation.components.PokemonText

@Composable
fun PokemonListScreen(
    pokemonViewModel: PokemonViewModel = hiltViewModel(),
    navigateToDetailPage: (pokemonId : Int) -> Unit,
) {
    val result = pokemonViewModel.state.pokemonList.collectAsLazyPagingItems()
    val loadState = result.loadState

    LazyColumn(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = result.itemCount,
            key = result.itemKey { it.id }
        ) { index ->
            val pokemon = result[index]
            if (pokemon != null) {
                PokemonItem(
                    pokemon = pokemon,
                    onClick = {
                        navigateToDetailPage(pokemon.id)
                    }
                )
            }
        }

        result.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        PokemonLoading(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(8.dp)
                        )
                    }
                }

                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize()
                        ) {
                            PokemonLoading(
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item {
                        error.error.localizedMessage?.let {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = it,
                                onClickRetry = { retry() })
                        }
                    }
                }


                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        error.error.localizedMessage?.let {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = it,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
) {
    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokemonText(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
        )

        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun PokemonLoading(
    modifier: Modifier = Modifier
) {

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading
        )
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        modifier = modifier
    )
}