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

    @Upsert
    suspend fun addPokemon(pokemon: PokemonEntity)

    @Query("DELETE FROM `POKEMON_TABLE`")
    suspend fun deleteAllPokemon()

    @Query("SELECT * FROM `POKEMON_TABLE`")
    fun getAllPokemon(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM `POKEMON_TABLE` WHERE id=:pokemonId")
    suspend fun getPokemonById(pokemonId : Int) : PokemonEntity
}