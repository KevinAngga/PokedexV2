package com.angga.pokedex.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.angga.pokedex.data.local.entity.PokemonRemoteKeysEntity

@Dao
interface PokemonRemoteKeysDao {
    @Upsert
    suspend fun addRemoteKeys(remoteKeys: List<PokemonRemoteKeysEntity>)

    @Query("DELETE FROM `POKEMON_REMOTE_KEYS`")
    suspend fun deleteAllRemoteKeys()

    @Query("SELECT * FROM `POKEMON_REMOTE_KEYS` WHERE id=:id")
    suspend fun getRemoteKeys(id : Int): PokemonRemoteKeysEntity
}