package com.angga.pokedex.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.angga.pokedex.domain.model.Pokemon

@Composable
fun PokemonItem(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Text(text = pokemon.name)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = pokemon.height.toString())
        Spacer(modifier = Modifier.height(6.dp))
    }
}