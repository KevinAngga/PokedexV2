package com.angga.pokedex.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.angga.pokedex.data.remote.utils.POKEMON_ABILITIES

@Entity(tableName = POKEMON_ABILITIES)
data class PokemonAbilityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @Embedded
    val ability : AbilityEntity,
    val isHidden : Boolean
)
