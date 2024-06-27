package com.angga.pokedex.data.remote.data_source

import com.angga.pokedex.data.remote.dto.PokemonDto
import com.angga.pokedex.data.remote.dto.PokemonListResponseDto
import com.angga.pokedex.data.remote.dto.toPokemon
import com.angga.pokedex.data.remote.utils.POKEMON
import com.angga.pokedex.data.remote.utils.get
import com.angga.pokedex.domain.data_source.RemoteDataSource
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import com.angga.pokedex.domain.utils.map
import io.ktor.client.HttpClient

class KtorRemoteDataSource(
    private val httpClient: HttpClient,
) : RemoteDataSource {
    override suspend fun getPokemon(): Result<List<Pokemon>, DataError.Network> {
      return httpClient.get<PokemonListResponseDto>(
          route = "$POKEMON",
          queryParameters = mapOf("limit" to 10, "offset" to 0),
      ).map {
         it.results.map {
             println(it)
             it.toPokemon()
         }
      }
    }

    override suspend fun getPokemonDetail(pokemonName: String): Result<Pokemon, DataError.Network> {
        return httpClient.get<PokemonDto>(route = "$POKEMON/$pokemonName").map {
            it.toPokemon()
        }
    }
}