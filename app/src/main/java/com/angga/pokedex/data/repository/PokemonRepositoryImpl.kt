package com.angga.pokedex.data.repository

import com.angga.pokedex.domain.data_source.RemoteDataSource
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.repository.PokemonRepository
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result

class PokemonRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): PokemonRepository {
    override suspend fun getPokemonList(): Result<List<Pokemon>, DataError.Network> {
        return remoteDataSource.getPokemon()
    }
}