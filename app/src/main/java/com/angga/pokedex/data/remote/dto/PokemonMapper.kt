package com.angga.pokedex.data.remote.dto

import com.angga.pokedex.domain.model.Pokemon

fun PokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        name = name
    )
}

fun PokemonListDto.toPokemon(): Pokemon {
    return Pokemon(
        name = name
    )
}