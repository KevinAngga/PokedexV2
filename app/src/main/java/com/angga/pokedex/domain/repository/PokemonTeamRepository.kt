package com.angga.pokedex.domain.repository

import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface PokemonTeamRepository {
    suspend fun inputPokemonToTeam(pokemon: Pokemon) : Result<String, DataError.Local>
    suspend fun deletePokemonFromTeam(pokemon: Pokemon) : Result<String, DataError.Local>
    suspend fun getAllPokemonTeam() : Flow<List<PokemonTeam>>
    suspend fun getImageFromUrlAndSaveFiles(pokemon: Pokemon) : String
}