package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id : Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types : List<PokemonTypeDto>,
    val abilities : List<PokemonAbilityDto>
)
