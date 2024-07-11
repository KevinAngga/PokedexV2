package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id : Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities : List<PokemonAbilityDto>,
    val types : List<TypeDto>
)
