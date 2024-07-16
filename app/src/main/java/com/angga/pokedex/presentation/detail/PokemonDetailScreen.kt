package com.angga.pokedex.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PokemonDetailScreen(pokemonId: Int) {
    Column {
        Text(text = "detail ${pokemonId}")
    }
}