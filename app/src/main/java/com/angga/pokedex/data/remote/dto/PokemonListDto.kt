package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListDto(
    val name: String,
    val url: String,
)
