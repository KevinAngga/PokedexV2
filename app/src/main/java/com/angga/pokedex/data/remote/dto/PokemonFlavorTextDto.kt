package com.angga.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonFlavorTextDto(
    @SerialName("flavor_text")
    val flavorText : String,
    val language : LanguageDto,
    val version : VersionDto
)
