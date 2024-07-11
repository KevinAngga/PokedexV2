package com.angga.pokedex.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.angga.pokedex.data.remote.utils.POKEMON_TABLE

@Entity(tableName = POKEMON_TABLE)
data class PokemonEntity(
    @PrimaryKey
    val id : Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @Embedded(prefix = "abilities_")
    val abilities : String,
    @Embedded(prefix = "types_")
    val types : String
)