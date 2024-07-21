package com.angga.pokedex.presentation.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.presentation.components.PokemonText
import com.angga.pokedex.presentation.ui.theme.PokedexTheme
import com.angga.pokedex.presentation.ui.theme.ShadowText

@Composable
fun PokemonTeamItem(
    index: Int,
    pokemonTeam: PokemonTeam
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    if (index.plus(1) % 2 == 0) {
                        ShadowText.copy(alpha = 0.1f)
                    } else {
                        Color.White
                    }
                )
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(70.dp),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(pokemonTeam.getSpriteImageUrl())
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
            )

            Spacer(modifier = Modifier.width(24.dp))

            PokemonText(
                text = pokemonTeam.name.replaceFirstChar { it.uppercase() },
                fontSize = 20.sp
            )
        }
    }
}


@Preview
@Composable
fun PokemonTeamItemPreview() {
    PokedexTheme {
        PokemonTeamItem(
            index = 0,
            pokemonTeam = PokemonTeam(
                id = 1,
                name = "bulbasaur",
                type1 = "grass",
                type2 = "poison"
            )
        )
    }
}

