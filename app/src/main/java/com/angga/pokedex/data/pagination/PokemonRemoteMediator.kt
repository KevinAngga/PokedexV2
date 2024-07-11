package com.angga.pokedex.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.data.local.entity.PokemonRemoteKeysEntity
import com.angga.pokedex.data.remote.dto.toPokemonEntity
import com.angga.pokedex.domain.data_source.RemoteDataSource
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonDatabase: PokemonDatabase,
    private val remoteDataSource: RemoteDataSource,
) : RemoteMediator<Int, PokemonEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>,
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 0
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            var endOfPaginationReached = false
            val result = mutableListOf<Pokemon>()

            val response = remoteDataSource.getPokemon(
                    limit = 10,
                    offset = currentPage.times(10)
                )

            response.map { pokemonList ->
//                endOfPaginationReached = pokemonList.isEmpty()
                if (pokemonList.isNotEmpty()) {
                    pokemonList.forEach { pokemon ->
                        withContext(Dispatchers.IO) {
                            async {
                                val withDetail = remoteDataSource.getPokemonDetail(pokemon.name)
                                withDetail.map { pokemonWithDetail ->
                                    result.add(pokemonWithDetail)
                                }
                            }
                        }
                    }
                }
            }

            val prePage = if (currentPage == 0) null else currentPage - 1
            val nextPage = if (result.size == 1302) null else currentPage + 1

            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDatabase.pokemonDao.deleteAllPokemon()
                    pokemonDatabase.pokemonRemoteKeysDao.deleteAllRemoteKeys()
                }

                val keys = result.map {
                    PokemonRemoteKeysEntity(
                        id = it.id,
                        prevKey = prePage,
                        nextKey = nextPage
                    )
                }

                pokemonDatabase.pokemonRemoteKeysDao.addRemoteKeys(keys)
                pokemonDatabase.pokemonDao.addAllPokemon(
                    result.map {
                        it.toPokemonEntity()
                    }
                )
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: HttpException) {
            println("=== e " + e.localizedMessage)
            MediatorResult.Error(e)
        } catch (e: Exception) {
            println("=== e" + e.localizedMessage)
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(
        state: PagingState<Int, PokemonEntity>,
    ): PokemonRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pokemonDatabase.pokemonRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PokemonEntity>,
    ): PokemonRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { it ->
            pokemonDatabase.pokemonRemoteKeysDao.getRemoteKeys(id = it.id)
        }
    }

    private suspend fun getRemoteKeysForLastItem(
        state: PagingState<Int, PokemonEntity>,
    ): PokemonRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { it ->
            pokemonDatabase.pokemonRemoteKeysDao.getRemoteKeys(id = it.id)
        }
    }
}

