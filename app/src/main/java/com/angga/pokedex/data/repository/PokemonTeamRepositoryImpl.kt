package com.angga.pokedex.data.repository

import android.content.Context
import android.database.sqlite.SQLiteFullException
import com.angga.pokedex.data.local.PokemonDatabase
import com.angga.pokedex.data.remote.dto.toPokemonTeam
import com.angga.pokedex.data.remote.dto.toPokemonTeamEntity
import com.angga.pokedex.domain.model.Pokemon
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.domain.repository.PokemonTeamRepository
import com.angga.pokedex.domain.utils.DataError
import com.angga.pokedex.domain.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PokemonTeamRepositoryImpl(
    private val context: Context,
    private val pokemonDatabase: PokemonDatabase,
    private val httpClient: HttpClient
) : PokemonTeamRepository {
    override suspend fun inputPokemonToTeam(pokemon: Pokemon): Result<String, DataError.Local> {
        return try {
            withContext(Dispatchers.IO) {
                val currentTeamSize = getAllPokemonTeam().first().size

                if (currentTeamSize == 6) {
                    return@withContext Result.Failed(DataError.Local.MAX_POKEMON_TEAM)
                }

                val imagePath = async { getImageFromUrlAndSaveFiles(pokemon) }.await()
                var pokemonEntity = pokemon.toPokemonTeamEntity()

                pokemonEntity = pokemonEntity.copy(
                    localPath = imagePath
                )

                pokemonDatabase.pokemonTeamDao.addPokemonTeam(pokemonEntity)
                Result.Success(pokemonEntity.name)
            }
        } catch (e : SQLiteFullException) {
            Result.Failed(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deletePokemonFromTeam(pokemon: Pokemon): Result<String, DataError.Local> {
        pokemonDatabase.pokemonTeamDao.deletePokemon(pokemon.toPokemonTeamEntity())
        return Result.Success(pokemon.name)
    }

    override suspend fun getAllPokemonTeam(): Flow<List<PokemonTeam>> {
        return pokemonDatabase.pokemonTeamDao.getPokemonTeam().map { list ->
            list.map { pokemonTeamEntity ->
                pokemonTeamEntity.toPokemonTeam()
            }
        }
    }

    override suspend fun getImageFromUrlAndSaveFiles(pokemon: Pokemon): String {
        val response = httpClient.get(pokemon.getSpriteImageUrl())
        val imageBytes = if (response.status.value == 200) response.readBytes() else null
        val imageFile = File(context.filesDir, "${pokemon.name}.jpg")

        if (imageBytes != null) {
            // Delete the previous image
            context.filesDir.listFiles()?.let {
                for (file in it) {
                    if (file.path.contains(pokemon.name) && file.path == imageFile.path) {
                        file.delete()
                    }
                }
            }

            // Create an image file and write bytes to that file
            withContext(Dispatchers.IO) {
                FileOutputStream(imageFile).use { stream ->
                    stream.write(imageBytes)
                }
            }
        }

        return imageFile.path
    }
}