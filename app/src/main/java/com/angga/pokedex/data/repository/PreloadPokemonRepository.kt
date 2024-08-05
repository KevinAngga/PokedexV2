package com.angga.pokedex.data.repository

import android.content.SharedPreferences
import com.angga.pokedex.data.local.DATA_LOADED_PREF
import com.angga.pokedex.data.local.dao.PokemonDao
import com.angga.pokedex.data.local.entity.PokemonEntity
import javax.inject.Inject

class PreloadPokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val editor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun insertData(pokemonEntity: PokemonEntity) {
        pokemonDao.addPokemon(pokemonEntity)
    }

    fun isDataLoaded() : Boolean {
        return sharedPreferences.getBoolean(DATA_LOADED_PREF, false)
    }

    fun setDataLoaded() {
        editor.putBoolean(DATA_LOADED_PREF, true)
    }
}