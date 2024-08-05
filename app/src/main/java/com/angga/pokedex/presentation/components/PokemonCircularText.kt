package com.angga.pokedex.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angga.pokedex.presentation.utils.PokemonTypeColour.Companion.getTypeBackground

@Composable
fun PokemonCircularText(text: String) {
    Box(
        modifier = Modifier
            .width(102.dp)
            .clip(RoundedCornerShape(48.dp))
            .background(color = text.getTypeBackground().typeColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.replaceFirstChar { it.uppercase() },
            fontSize = 14.sp,
            color = Color.White
        )
    }
}