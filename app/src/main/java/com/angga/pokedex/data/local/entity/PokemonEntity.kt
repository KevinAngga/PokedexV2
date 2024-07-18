package com.angga.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.angga.pokedex.data.remote.utils.POKEMON_TABLE

@Entity(tableName = POKEMON_TABLE)
data class PokemonEntity(
    @PrimaryKey
    val id : Int,
    val name: String,
    val weight : Int,
    val height: Int,
    val type1: String?,
    val type2: String?,
    val ability1: String?,
    val ability2: String?,
)