package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponseDto(
    val results : List<PokemonListDto>
)
