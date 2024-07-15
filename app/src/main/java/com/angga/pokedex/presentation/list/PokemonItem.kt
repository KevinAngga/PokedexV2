package com.angga.pokedex.presentation.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.angga.pokedex.R
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.presentation.components.PokemonCircularText
import com.angga.pokedex.presentation.components.PokemonText
import com.angga.pokedex.presentation.ui.theme.NormalType
import com.angga.pokedex.presentation.utils.formatNumberWithLeadingZeros

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onClick : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        var expanded by remember { mutableStateOf(false) }
        val targetWidth = if (expanded) LocalConfiguration.current.screenWidthDp.dp
        else LocalConfiguration.current.screenWidthDp.dp * 0.65f
        val animatedWidth by animateDpAsState(targetValue = targetWidth, label = "")


        val defaultDominantColor = NormalType
        var dominantColor by remember {
            mutableStateOf(defaultDominantColor)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .clip(RoundedCornerShape(24.dp))
        ) {
            Box(
                modifier = Modifier
                    .background(dominantColor)
                    .fillMaxHeight()
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                expanded = true
                            }
                            MotionEvent.ACTION_UP,
                            MotionEvent.ACTION_CANCEL,
                            MotionEvent.ACTION_MOVE -> {
                                expanded = false
                            }
                        }
                        true
                    }
                    .width(animatedWidth)
                    .clip(RoundedCornerShape(24.dp))
                    .align(Alignment.CenterEnd)
            ) {

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(24.dp))
                    .zIndex(1f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        PokemonText(
                            text = pokemon.name.replaceFirstChar { it.uppercase() },

                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        PokemonText(
                            text = pokemon.id.toString().formatNumberWithLeadingZeros()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        pokemon.types[1]?.let { type2 ->
                            if (type2.isEmpty()) { Spacer(modifier = Modifier.height(40.dp)) }
                            pokemon.types[0]?.let { PokemonCircularText(text = it) }
                            Spacer(modifier = Modifier.height(4.dp))
                            if (type2.isNotEmpty()) {
                                PokemonCircularText(text = type2)
                            }
                        }

                    }

                    Box(
                        modifier = Modifier
                            .width(LocalConfiguration.current.screenWidthDp.dp * 0.65f),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = AbsoluteAlignment.Right
                        ) {
                            Image(
                                painterResource(id = R.drawable.pokeball),
                                modifier = Modifier
                                    .size(130.dp)
                                    .offset(y = (-15).dp)
                                    .padding(end = 8.dp),
                                contentDescription = "backImage",
                                contentScale = ContentScale.Inside,
                            )
                        }
                    }
                }
            }
        }

        AsyncImage(
            modifier = Modifier
                .size(150.dp)
                .offset(
                    x = 40.dp,
                    y = (-10).dp
                ).clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                  onClick()
                },
            model = pokemon.getImageUrl(),
            contentDescription = "",
            onSuccess = {
                calcDominantColor(drawable = it.result.drawable) {
                    dominantColor = it
                }
            },
            onError = {
                dominantColor = NormalType
            },
            contentScale = ContentScale.FillHeight,
        )
    }

}

fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
    val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

    Palette.from(bmp).generate { palette ->
        palette?.dominantSwatch?.rgb?.let { colorValue ->
            onFinish(Color(colorValue))
        }
    }
}

@Preview
@Composable
private fun PokemonItemPreview() {
    MaterialTheme {
        PokemonItem(
            onClick = {},
            pokemon = Pokemon(
                id = 1,
                name = "bulbasaur",
                url = "",
                height = 0,
                types = listOf(
                    "poison",
                    "grass"
                )
            )
        )
    }
}