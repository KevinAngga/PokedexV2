package com.angga.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.angga.pokedex.data.local.dao.PokemonDao
import com.angga.pokedex.data.local.dao.PokemonRemoteKeysDao
import com.angga.pokedex.data.local.entity.PokemonAbilityEntity
import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.data.local.entity.PokemonRemoteKeysEntity
import com.angga.pokedex.data.local.entity.TypeEntity

@Database(entities = [PokemonEntity::class, PokemonRemoteKeysEntity::class,
        PokemonAbilityEntity::class, TypeEntity::class], version = 1, exportSchema = false)

@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao : PokemonDao
    abstract val pokemonRemoteKeysDao : PokemonRemoteKeysDao
}