package com.angga.pokedex.domain.model

data class Pokemon(
    val id : Int = 0,
    val name: String = "",
    val url: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val abilities : List<PokemonAbility> = emptyList(),
    val types : List<Type> = emptyList()
)