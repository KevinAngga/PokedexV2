package com.angga.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.angga.pokedex.data.local.POKEMON_TEAM

@Entity(tableName = POKEMON_TEAM)
data class PokemonTeamEntity(
    @PrimaryKey
    val id : Int,
    val name : String,
    val localPath : String,
    val type1 : String,
    val type2: String
)