package com.angga.pokedex.presentation.detail

import com.angga.pokedex.domain.model.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon = Pokemon(
        id = 1,
        name = "bulbasaur",
        url = "",
        height = 0,
        types = listOf(
            "poison",
            "grass"
        ),
        abilities = listOf(
            "overgrow",
            "chlorophyll"
        )
    ),
    val pokemonDesc : String = "-",
    val habitat : String = "-",
    val characteristic : String = "-",
    val isLoading : Boolean = false
)
