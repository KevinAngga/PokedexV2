package com.angga.pokedex.presentation.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.Text
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.presentation.MainActivity

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
            initial = listOf()
        ).value

        val context = LocalContext.current

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .background(Color.White),
        ) {
            LazyColumn(
                modifier = GlanceModifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = list,
                ) { index, pokemon ->
                    WidgetItem(
                        pokemonTeam = pokemon,
                        onClick = actionStartActivity(
                            Intent(
                                context.applicationContext,
                                MainActivity::class.java)
                                .setAction(Intent.ACTION_VIEW)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                    )
                }
            }
        }
    }
    
    @Composable
    private fun WidgetItem(
        pokemonTeam: PokemonTeam,
        onClick: Action
    ) {

        val filePathString: String = remember {
            pokemonTeam.localPath
        }

        val bitmap = rememberImageBitmap(filePathString)

        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(onClick),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Image(provider = ImageProvider(bitmap), contentDescription = pokemonTeam.name)
            
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