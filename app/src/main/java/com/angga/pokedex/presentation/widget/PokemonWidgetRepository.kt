package com.angga.pokedex.presentation.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.angga.pokedex.data.local.dao.PokemonTeamDao
import com.angga.pokedex.data.local.entity.PokemonTeamEntity
import com.angga.pokedex.data.remote.dto.toPokemonTeam
import com.angga.pokedex.di.AppCoroutineScope
import com.angga.pokedex.domain.model.PokemonTeam
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonWidgetRepository @Inject internal  constructor(
    private val pokemonTeamDao: PokemonTeamDao,
    @AppCoroutineScope private val coroutineScope: CoroutineScope,
    @ApplicationContext private val appContext: Context,
) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface PokemonWidgetRepositoryEntryoint {
        fun pokemonWidgetRepository(): PokemonWidgetRepository
    }

    companion object {
        fun get(applicationContext: Context): PokemonWidgetRepository {
            var pokemonWidgetRepository: PokemonWidgetRepositoryEntryoint = EntryPoints.get(
                applicationContext,
                PokemonWidgetRepositoryEntryoint::class.java,
            )
            return pokemonWidgetRepository.pokemonWidgetRepository()
        }
    }

    fun loadModel() : Flow<List<PokemonTeam>> {
        return pokemonTeamDao.getPokemonTeam().distinctUntilChanged().map {
            it.map { pokemonTeamEntity ->
                pokemonTeamEntity.toPokemonTeam()
            }
        }
    }

}