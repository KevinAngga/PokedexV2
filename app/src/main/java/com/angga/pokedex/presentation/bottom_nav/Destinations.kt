package com.angga.pokedex.presentation.bottom_nav

import com.angga.pokedex.domain.model.Pokemon
import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object Home

    @Serializable
    data object Team

    @Serializable
    data class Detail(
        val pokemonId: Int
    )

    @Serializable
    data object BottomNavGraph

    @Serializable
    data object DetailGraph
}