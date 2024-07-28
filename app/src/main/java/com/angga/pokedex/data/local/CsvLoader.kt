package com.angga.pokedex.data.local

import android.content.Context
import com.angga.pokedex.R
import com.angga.pokedex.data.local.entity.PokemonEntity
import com.angga.pokedex.data.repository.PreloadPokemonRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CsvLoader @Inject constructor(
    private val preloadPokemonRepository: PreloadPokemonRepository,
    @ApplicationContext private val context: Context,
) {
    suspend fun loadCsvData() {
        if (!preloadPokemonRepository.isDataLoaded()) {
            val inputStream = context.resources.openRawResource(R.raw.pokemon_table)
            val reader = inputStream.bufferedReader()
            reader.useLines { lines ->
                lines.forEach { line ->
                    val input = line.split(";")
                    val pokemon = PokemonEntity(
                        id = input[0].trim().toInt(),
                        name = input[1].trim(),
                        weight = input[2].trim().toInt(),
                        height = input[3].trim().toInt(),
                        type1 = input[4].trim(),
                        type2 = input[5].trim(),
                        ability1 = input[6].trim(),
                        ability2 = input[7].trim(),
                    )
                    preloadPokemonRepository.insertData(pokemon)
                }
            }

            preloadPokemonRepository.setDataLoaded()
        }
    }
}