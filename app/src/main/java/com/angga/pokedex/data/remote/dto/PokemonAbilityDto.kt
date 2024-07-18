package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityDto(
    val ability : AbilityDto,
    @SerialName("is_hidden")
    val isHidden : Boolean,
    val slot : Int
)
