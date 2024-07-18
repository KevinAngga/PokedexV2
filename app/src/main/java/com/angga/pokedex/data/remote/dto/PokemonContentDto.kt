package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonContentDto(
    @SerialName("flavor_text_entries")
    val flavorTextEntries : List<PokemonFlavorTextDto>,
    val habitat : PokemonHabitatDto
)