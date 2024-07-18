package com.angga.pokedex.data.remote.dto

import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonCharacteristic
import com.angga.pokedex.domain.model.PokemonDesc

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        weight = weight,
        height = height,
        type1 = types[0].type.name,
        type2 = if (types.size > 1) types[1].type.name else null,
        ability1 = abilities[0].ability.name,
        ability2 = if (abilities.size > 1) abilities[1].ability.name else null,
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        weight = weight,
        height = height,
        types = listOf(type1, type2),
        abilities = listOf(ability1, ability2)
    )
}

fun PokemonContentDto.toPokemonDesc() : PokemonDesc {
    return PokemonDesc(
        desc = this.flavorTextEntries.find { it.version.name == "white" && it.language.name == "en" }?.flavorText?.replace("\n", " ") ?: "-",
        habitat = habitat.name
    )
}

fun PokemonCharacteristicDto.toPokemonCharacteristic() : PokemonCharacteristic {
    return PokemonCharacteristic(
        characteristic = this.descriptions.find { it.language.name == "en" }?.description ?: "-"
    )
}