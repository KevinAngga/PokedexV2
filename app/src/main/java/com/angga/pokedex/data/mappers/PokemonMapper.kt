package com.angga.pokedex.data.mappers

import com.angga.pokedex.data.local.Converters
import com.angga.pokedex.data.local.entity.AbilityEntity
import com.angga.pokedex.data.local.entity.PokemonAbilityEntity
import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.data.local.entity.TypeEntity
import com.angga.pokedex.data.remote.dto.AbilityDto
import com.angga.pokedex.data.remote.dto.PokemonAbilityDto
import com.angga.pokedex.data.remote.dto.PokemonDto
import com.angga.pokedex.data.remote.dto.TypeDto
import com.angga.pokedex.domain.model.Ability
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonAbility
import com.angga.pokedex.domain.model.Type


fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        abilities = Converters().fromPokemonAbilityList(abilities.map { it.toPokemonAbilityEntity() }),
        types = Converters().fromTypeList(types.map { it.toTypeEntity() })
    )
}


fun Pokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        abilities = Converters().fromPokemonAbilityList(abilities.map { it.toPokemonAbilityEntity() }),
        types = Converters().fromTypeList(types.map { it.toTypeEntity() })
    )
}

fun PokemonAbility.toPokemonAbilityEntity() : PokemonAbilityEntity {
    return PokemonAbilityEntity(
        id = 0,
        isHidden = isHidden,
        ability = ability.toAbilityEntity()
    )
}

fun Ability.toAbilityEntity() : AbilityEntity {
    return AbilityEntity(
        name = name
    )
}

fun PokemonAbilityDto.toPokemonAbilityEntity() : PokemonAbilityEntity {
    return PokemonAbilityEntity(
        id = 0,
        isHidden = isHidden,
        ability = ability.toAbilityEntity()
    )
}

fun AbilityDto.toAbilityEntity() : AbilityEntity {
    return AbilityEntity(
        name = name
    )
}

fun Type.toTypeEntity() : TypeEntity {
    return TypeEntity(
        id = 0,
        name = name
    )
}

fun TypeDto.toTypeEntity() : TypeEntity {
    return TypeEntity(
        id = 0,
        name = name
    )
}



fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        height = height,

    )
}