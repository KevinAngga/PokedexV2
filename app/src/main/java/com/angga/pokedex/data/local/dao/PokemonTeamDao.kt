package com.angga.pokedex.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angga.pokedex.data.local.entity.PokemonTeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonTeamDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addPokemonTeam(pokemon : PokemonTeamEntity)

  @Delete
  suspend fun deletePokemon(pokemon: PokemonTeamEntity)

  @Query("SELECT * FROM `POKEMON_TEAM`")
  fun getPokemonTeam() : Flow<List<PokemonTeamEntity>>
}