package com.angga.pokedex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.angga.pokedex.presentation.ui.theme.PokedexTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                    Greeting(
                        pokemonViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    pokemonViewModel: PokemonViewModel,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = { pokemonViewModel.getPokemon() }
    ) {
        Text(
            text = "Hit Mee"
        )
    }
}
