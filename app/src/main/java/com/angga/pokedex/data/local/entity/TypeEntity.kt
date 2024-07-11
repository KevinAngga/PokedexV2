package com.angga.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.angga.pokedex.data.remote.utils.POKEMON_TYPES

@Entity(tableName = POKEMON_TYPES)
data class TypeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name : String
)
