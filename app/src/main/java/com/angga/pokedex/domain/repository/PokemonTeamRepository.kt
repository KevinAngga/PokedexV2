package com.angga.pokedex.domain.repository

import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonTeam
import kotlinx.coroutines.flow.Flow

interface PokemonTeamRepository {
    suspend fun inputPokemonToTeam(pokemon: Pokemon)
    suspend fun deletePokemonFromTeam(pokemon: Pokemon)
    suspend fun getAllPokemonTeam() : Flow<List<PokemonTeam>>
}