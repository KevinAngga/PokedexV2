package com.angga.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angga.pokedex.data.local.dao.PokemonDao
import com.angga.pokedex.data.local.dao.PokemonRemoteKeysDao
import com.angga.pokedex.data.local.dao.PokemonTeamDao
import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.data.local.entity.PokemonRemoteKeysEntity
import com.angga.pokedex.data.local.entity.PokemonTeamEntity

@Database(
    entities = [PokemonEntity::class,
        PokemonRemoteKeysEntity::class,
        PokemonTeamEntity::class],
    version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao : PokemonDao
    abstract val pokemonRemoteKeysDao : PokemonRemoteKeysDao
    abstract val pokemonTeamDao : PokemonTeamDao
}