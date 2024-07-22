package com.angga.pokedex.presentation.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.Text
import com.angga.pokedex.R
import com.angga.pokedex.data.local.entity.PokemonTeamEntity
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.presentation.components.PokemonText
import com.angga.pokedex.presentation.team.PokemonTeamItem
import java.io.File

class PokedexAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val repository = PokemonWidgetRepository.get(context)

        provideContent {
            GlanceTheme {
                PokedexWidget(
                    repository = repository
                )
            }
        }
    }

    @Composable
    private fun PokedexWidget(
        repository: PokemonWidgetRepository
    ) {
        val list = repository.loadModel().collectAsState(
            initial = listOf(
                PokemonTeam()
            )
        ).value

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyColumn {
                itemsIndexed(
                    items = list,
                ) { index, pokemon ->
                    WidgetItem(pokemonTeam = pokemon)
                }
            }
        }
    }
    
    @Composable
    private fun WidgetItem(pokemonTeam: PokemonTeam) {

        val pref = currentState<Preferences>()

        val filePathString: String = remember {
            pokemonTeam.localPath
        }
        val bitmap = rememberImageBitmap(filePathString)

        Row(
            modifier = GlanceModifier.fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Image(provider = ImageProvider(bitmap), contentDescription = "home")
            
            Spacer(modifier = GlanceModifier.width(4.dp))
            
            Text(
                modifier = GlanceModifier.padding(bottom = 2.dp),
                text = pokemonTeam.name.replaceFirstChar { it.uppercase() }
            )
        }
    }

    @Composable
    private fun rememberImageBitmap(filePathString: String): Bitmap {
        val context = LocalContext.current
        return remember {
            if (filePathString.isEmpty()) {
                val file = context.applicationContext.filesDir
                    .listFiles()
                    ?.find { it.path.endsWith(".jpg") }
                val path = file?.toURI()?.path
                BitmapFactory.decodeFile(path)
            } else
                BitmapFactory.decodeFile(filePathString)
        }
    }
}