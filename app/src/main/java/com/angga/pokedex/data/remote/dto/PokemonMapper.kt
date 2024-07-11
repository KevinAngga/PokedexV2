package com.angga.pokedex.data.remote.dto

import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.domain.model.Pokemon

fun PokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        height = height
    )
}

fun PokemonListDto.toPokemon(): Pokemon {
    return Pokemon(
        name = name
    )
}

fun Pokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        height = height
    )
}