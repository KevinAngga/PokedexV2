package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeDto(
    val slot : Int,
    val type : TypeDto
)
