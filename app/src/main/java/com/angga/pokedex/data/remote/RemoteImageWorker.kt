package com.angga.pokedex.data.remote

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.angga.pokedex.presentation.widget.PokedexAppWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


class RemoteImageWorker(
    val context: Context,
    val workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    private val httpClient : HttpClient = HttpClient(CIO) {

    }

    override suspend fun doWork(): Result {
        return try {
            val url = inputData.getString("IMAGE_URL") ?: ""
            val pokemonName = inputData.getString("POKEMON_NAME") ?: ""

            println("===== url "+url)
            println("===== pokemonName "+pokemonName)

            val response = httpClient.get(url)
            val imageBytes = if (response.status.value == 200) response.readBytes() else null
            println("===== response "+response.status.value)
            if (imageBytes != null) {
                // Create an image file and write bytes to that file
                val imageFile = File(context.filesDir, "${pokemonName}.jpg")
                withContext(Dispatchers.IO) {
                    FileOutputStream(imageFile).use { stream ->
                        stream.write(imageBytes)
                    }
                }

                // Delete the previous image
                context.filesDir.listFiles()?.let {
                    for (file in it) {
                        if (file.path.endsWith(".jpg") && file.path != imageFile.path) {
                            file.delete()
                        }
                    }
                }

                // Update the widget
                println("===== jalan "+imageFile.path)
                updateRemoteImageWidget(pokemonName, imageFile.path)
                Result.success()
            } else {
                println("===== error")
                Result.failure(workDataOf(ERROR_MESSAGE to "Error occurred while retrieving image"))
            }


        } catch (e : Exception) {
            println(e.localizedMessage)
            println("===== error" +e.localizedMessage)
            Result.failure()
        }
    }

    companion object {
        const val ERROR_MESSAGE = "ERROR_MESSAGE"
    }

    private suspend fun updateRemoteImageWidget(pokemonName : String, imageUri : String) {
        GlanceAppWidgetManager(context).getGlanceIds(PokedexAppWidget::class.java).forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { pref ->
                pref[stringPreferencesKey(pokemonName)] = imageUri
            }
//            PokedexAppWidget().updateAll(context)
        }
    }
}