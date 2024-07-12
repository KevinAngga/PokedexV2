package com.angga.pokedex.domain.model

data class Pokemon(
    val id : Int = 0,
    val name: String = "",
    val url: String = "",
    val height: Int = 0,
    val types : List<String?> = listOf()
) {
    fun getSpriteImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/$id.png"
    }

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$id.png"
    }
}