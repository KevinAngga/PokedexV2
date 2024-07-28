package com.angga.pokedex.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.angga.pokedex.presentation.ui.theme.Archive
import com.angga.pokedex.presentation.ui.theme.InGlobal
import com.angga.pokedex.presentation.ui.theme.PokedexTheme

@Composable
fun PokemonText(
    modifier: Modifier = Modifier,
    text : String,
    fontSize : TextUnit = 14.sp,
    fontFamily: FontFamily = InGlobal,
    fontWeight: FontWeight = FontWeight.Normal,
    color : Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        color = color
    )
}

@Preview
@Composable
private fun PokemonTextPreview() {
    PokedexTheme {
        PokemonText(text = "test")
    }
}