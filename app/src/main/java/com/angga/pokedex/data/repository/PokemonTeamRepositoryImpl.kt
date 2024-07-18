package com.angga.pokedex.data.repository

import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.remote.dto.toPokemonTeam
import com.angga.pokedex.data.remote.dto.toPokemonTeamEntity
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonTeamRepositoryImpl(
    private val pokemonDatabase: PokemonDatabase
) : PokemonTeamRepository {
    override suspend fun inputPokemonToTeam(pokemon: Pokemon) {
        pokemonDatabase.pokemonTeamDao.addPokemonTeam(pokemon.toPokemonTeamEntity())
    }

    override suspend fun deletePokemonFromTeam(pokemon: Pokemon) {
        pokemonDatabase.pokemonTeamDao.deletePokemon(pokemon.toPokemonTeamEntity())
    }

    override suspend fun getAllPokemonTeam(): Flow<List<PokemonTeam>> {
        return pokemonDatabase.pokemonTeamDao.getPokemonTeam().map { list ->
            list.map { pokemonTeamEntity ->
                pokemonTeamEntity.toPokemonTeam()
            }
        }
    }
}