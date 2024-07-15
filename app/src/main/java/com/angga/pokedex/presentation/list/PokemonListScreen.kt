package com.angga.pokedex.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreen(
    pokemonViewModel: PokemonViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    navigateToDetailPage: () -> Unit,
) {
    val result = pokemonViewModel.state.pokemonList.collectAsLazyPagingItems()
    val loadState = result.loadState.mediator

    LazyColumn(
        modifier = Modifier
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
                        navigateToDetailPage()
                    }
                )
            }
        }

        result.apply {
            when {
                loadState?.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                        )
                    }
                }

                loadState?.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                loadState?.refresh is LoadState.Error -> {
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


                loadState?.append is LoadState.Error -> {
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
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "retry")
        }
    }
}