package com.angga.pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.remote.dto.PokemonCharacteristicDto
import com.angga.pokedex.data.remote.dto.PokemonContentDto
import com.angga.pokedex.data.remote.dto.toPokemon
import com.angga.pokedex.data.remote.dto.toPokemonCharacteristic
import com.angga.pokedex.data.remote.dto.toPokemonDesc
import com.angga.pokedex.data.remote.utils.get
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonCharacteristic
import com.angga.pokedex.domain.model.PokemonDesc
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import com.angga.pokedex.domain.utils.map
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val httpClient: HttpClient,
    private val pokemonDatabase: PokemonDatabase
): PokemonRepository {
    override suspend fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pokemonDatabase.pokemonDao.getAllPokemon() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toPokemon()
            }
        }
    }

    override suspend fun getPokemonDetail(pokemonId: Int): Pokemon {
        return pokemonDatabase.pokemonDao
            .getPokemonById(pokemonId)
            .toPokemon()
    }

    override suspend fun getPokemonContentDto(pokemonId: Int): Result<PokemonDesc, DataError.Network> {
        return httpClient.get<PokemonContentDto>(
            route = "pokemon-species/$pokemonId",
        ).map { it.toPokemonDesc() }
    }

    override suspend fun getPokemonCharacteristic(pokemonId: Int): Result<PokemonCharacteristic, DataError.Network> {
        return httpClient.get<PokemonCharacteristicDto>(
            route = "characteristic/$pokemonId",
        ).map { it.toPokemonCharacteristic() }
    }
}