package com.angga.pokedex.data.remote.data_source

import com.angga.pokedex.data.remote.dto.PokemonDto
import com.angga.pokedex.data.remote.dto.PokemonListDto
import com.angga.pokedex.data.remote.dto.PokemonListResponseDto
import com.angga.pokedex.data.remote.utils.POKEMON
import com.angga.pokedex.data.remote.utils.get
import com.angga.pokedex.domain.data_source.RemoteDataSource
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import com.angga.pokedex.domain.utils.map
import io.ktor.client.HttpClient

class KtorRemoteDataSource(
    private val httpClient: HttpClient,
) : RemoteDataSource {
    override suspend fun getPokemon(
        limit: Int,
        offset: Int
    ): Result<List<PokemonListDto>, DataError.Network> {
      return httpClient.get<PokemonListResponseDto>(
          route = POKEMON,
          queryParameters = mapOf("limit" to limit, "offset" to offset),
      ).map {
         it.results
      }
    }

    override suspend fun getPokemonDetail(pokemonName: String): Result<PokemonDto, DataError.Network> {
        return httpClient.get<PokemonDto>(route = "$POKEMON/$pokemonName")
    }
}