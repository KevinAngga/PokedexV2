package com.angga.pokedex.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.angga.pokedex.domain.data_source.RemoteDataSource
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.utils.map
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.SerializationException

class PokemonPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Pokemon>() {

    override val keyReuseSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPage ->
            val page = state.closestPageToPosition(anchorPage)
            page?.nextKey?.minus(1) ?: page?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val page = params.key ?: 0
        return try {
            val pokemonList = mutableListOf<Pokemon>()

            val pokemonResponse = remoteDataSource.getPokemon(limit = 10,
                offset = page.times(10))
//            pokemonResponse.map {
//                it.forEach { pokemon ->
//                    coroutineScope {
//                        async {
//                            val pokemon = remoteDataSource.getPokemonDetail(pokemon.name)
//                            pokemon.map { pokemonWithDetail ->
//                                pokemonList.add(pokemonWithDetail)
//                            }
//                        }.await()
//                    }
//                }
//            }

            LoadResult.Page(
                data = pokemonList,
                nextKey = if (pokemonList.size == 1302) null else page + 1,
                prevKey = null
            )
        } catch(e: UnresolvedAddressException) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        } catch (e: SerializationException) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        } catch(e: Exception) {
            if(e is CancellationException) throw e
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }

}