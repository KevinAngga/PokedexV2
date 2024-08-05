package com.angga.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.angga.pokedex.data.local.POKEMON_REMOTE_KEYS

@Entity(tableName = POKEMON_REMOTE_KEYS)
data class PokemonRemoteKeysEntity(
    @PrimaryKey
    val id : Int,
    val nextKey : Int?,
    val prevKey : Int?
)
