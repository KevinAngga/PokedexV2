package com.angga.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityDto(
    @SerializedName("ability")
    val ability : AbilityDto,
    @SerializedName("is_hidden")
    val isHidden : Boolean =false
)
