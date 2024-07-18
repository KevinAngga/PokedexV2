package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicDto(
    val description : String,
    val language: LanguageDto
)
