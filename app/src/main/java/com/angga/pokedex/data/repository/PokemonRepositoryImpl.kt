package com.angga.pokedex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.pagination.PokemonRemoteMediator
import com.angga.pokedex.data.remote.dto.toPokemon
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.repository.PokemonRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val httpClient: HttpClient,
    private val pokemonDatabase: PokemonDatabase
): PokemonRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PokemonRemoteMediator(
                httpClient = httpClient,
                pokemonDatabase = pokemonDatabase
            ),
            pagingSourceFactory = { pokemonDatabase.pokemonDao.getAllPokemon() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toPokemon()
            }
        }
    }
}