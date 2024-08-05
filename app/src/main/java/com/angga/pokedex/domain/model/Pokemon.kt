package com.angga.pokedex.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Pokemon(
    val id : Int = 0,
    val name: String = "",
    val url: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val types : List<String?> = listOf(),
    val abilities : List<String?> = listOf()
) {
    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$id.png"
    }

    fun getSpriteImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/$id.png"
    }
}