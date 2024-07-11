package com.angga.pokedex.data.local

import androidx.room.TypeConverter
import com.angga.pokedex.data.local.entity.PokemonAbilityEntity
import com.angga.pokedex.data.local.entity.TypeEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromPokemonAbilityList(abilities: List<PokemonAbilityEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonAbilityEntity>>() {}.type
        return gson.toJson(abilities, type)
    }

    @TypeConverter
    fun toPokemonAbilityList(abilityString: String): List<PokemonAbilityEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonAbilityEntity>>() {}.type
        return gson.fromJson(abilityString, type)
    }

    @TypeConverter
    fun fromTypeList(types: List<TypeEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<TypeEntity>>() {}.type
        return gson.toJson(types, type)
    }

    @TypeConverter
    fun toTypeList(typeString: String): List<TypeEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<TypeEntity>>() {}.type
        return gson.fromJson(typeString, type)
    }
}