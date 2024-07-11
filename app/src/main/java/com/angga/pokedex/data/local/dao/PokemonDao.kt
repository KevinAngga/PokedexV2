package com.angga.pokedex.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.angga.pokedex.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Upsert
    suspend fun addAllPokemon(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM `POKEMON_TABLE`")
    suspend fun deleteAllPokemon()

    @Query("SELECT * FROM `POKEMON_TABLE`")
    fun getAllPokemon(): PagingSource<Int, PokemonEntity>
}