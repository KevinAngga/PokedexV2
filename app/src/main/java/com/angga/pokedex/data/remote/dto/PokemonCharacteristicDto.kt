package com.angga.pokedex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonCharacteristicDto(
    val descriptions : List<CharacteristicDto>
)
