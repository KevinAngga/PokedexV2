package com.angga.pokedex.data.remote.dto

import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.domain.model.Pokemon

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        type1 = types[0].type.name,
        type2 = if (types.size > 1) types[1].type.name else "",
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        height = height,
        types = listOf(type1, type2)
    )
}