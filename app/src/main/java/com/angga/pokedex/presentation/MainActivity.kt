package com.angga.pokedex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.presentation.ui.theme.PokedexTheme
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {
    val pokemonViewModel by viewModel<PokemonViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    PokemonListScreen(
                        pokemonViewModel = pokemonViewModel,
                        modifier = Modifier.fillMaxSize()
                    )

//                    PokemonItem(pokemon = Pokemon(
//                        id = 1,
//                        name = "bulbasaur",
//                        url = "",
//                        height = 0,
//                        types = listOf(
//                            "poison",
//                            "grass"
//                        )
//                    ))
                }
            }
        }
    }
}

@Composable
fun PokemonListScreen(
    pokemonViewModel: PokemonViewModel,
    modifier: Modifier = Modifier
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
            key = { item -> item }
        ) { index ->
            val pokemon = result[index]
            if (pokemon != null) {
                PokemonItem(
                    pokemon = pokemon,
                    onClick = {
                        Timber.e("clicked")
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
