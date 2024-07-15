package com.angga.pokedex.presentation.bottom_nav

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object Home

    @Serializable
    data object Team

    @Serializable
    data object Detail

    @Serializable
    data object BottomNavGraph

    @Serializable
    data object DetailGraph
}