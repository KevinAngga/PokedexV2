package com.angga.pokedex.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.angga.pokedex.presentation.ui.theme.Shimmer


fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value
    background(color = Shimmer.copy(alpha = alpha))
}

@Composable
fun PokemonDetailShimmer() {
   Column(
       modifier = Modifier
       .padding(16.dp)
   ) {
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(20.dp)
               .shimmerEffect()
       )

       Spacer(modifier = Modifier.height(16.dp))

       Box(
           modifier = Modifier
               .width(70.dp)
               .height(20.dp)
               .shimmerEffect()
       )

       Spacer(modifier = Modifier.height(16.dp))

       Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(20.dp)
               .shimmerEffect()
       )

       Spacer(modifier = Modifier.height(16.dp))

       Box(
           modifier = Modifier
               .width(70.dp)
               .height(20.dp)
               .shimmerEffect()
       )

       Spacer(modifier = Modifier.height(16.dp))

       Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(20.dp)
               .shimmerEffect()
       )

   }
}

@Preview
@Composable
fun PokemonDetailShimmerPreview() {
    PokemonDetailShimmer()
}