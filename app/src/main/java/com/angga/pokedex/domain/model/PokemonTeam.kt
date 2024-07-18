package com.angga.pokedex.domain.model

data class PokemonTeam(
    val id : Int = 0,
    val name : String = "",
    val type1 : String = "",
    val type2: String = ""
) {
    fun getSpriteImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/$id.png"
    }
}
