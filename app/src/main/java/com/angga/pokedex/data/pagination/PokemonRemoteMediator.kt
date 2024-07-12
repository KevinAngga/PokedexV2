package com.angga.pokedex.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.data.local.entity.PokemonRemoteKeysEntity
import com.angga.pokedex.data.remote.dto.PokemonDto
import com.angga.pokedex.data.remote.dto.PokemonListResponseDto
import com.angga.pokedex.data.remote.dto.toPokemonEntity
import com.angga.pokedex.data.remote.utils.LIMIT
import com.angga.pokedex.data.remote.utils.OFFSET
import com.angga.pokedex.data.remote.utils.POKEMON
import com.angga.pokedex.data.remote.utils.get
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import com.angga.pokedex.domain.utils.map
import io.ktor.client.HttpClient
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonDatabase: PokemonDatabase,
    private val httpClient: HttpClient,
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
            val result = mutableListOf<PokemonEntity>()

            val response = httpClient.get<PokemonListResponseDto>(
                route = POKEMON,
                queryParameters = mapOf(LIMIT to 10, OFFSET to currentPage.times(10)),
            ).map {
                it.results
            }

            response.map { pokemonList ->
                endOfPaginationReached = pokemonList.isEmpty()

                if (loadType == LoadType.REFRESH) {
                    pokemonDatabase.pokemonDao.deleteAllPokemon()
                    pokemonDatabase.pokemonRemoteKeysDao.deleteAllRemoteKeys()
                }

                if (pokemonList.isNotEmpty()) {
                    pokemonList.forEach { pokemon ->
                        withContext(Dispatchers.IO) {
                            async {
                                val withDetail = httpClient.get<PokemonDto>(route = "$POKEMON/${pokemon.name}")
                                withDetail.map { pokemonWithDetail ->
                                    result.add(pokemonWithDetail.toPokemonEntity())
                                }
                            }.await()
                        }
                    }
                }
            }

            val prePage = if (currentPage == 0) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            pokemonDatabase.withTransaction {
                val keys = result.map {
                    PokemonRemoteKeysEntity(
                        id = it.id,
                        prevKey = prePage,
                        nextKey = nextPage
                    )
                }

                pokemonDatabase.pokemonRemoteKeysDao.addRemoteKeys(keys)
                pokemonDatabase.pokemonDao.addAllPokemon(result)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch(e: UnresolvedAddressException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: SerializationException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: Exception) {
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

