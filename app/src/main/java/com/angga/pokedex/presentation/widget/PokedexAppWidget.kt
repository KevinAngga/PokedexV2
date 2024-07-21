package com.angga.pokedex.presentation.widget

import android.content.Context
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import com.angga.pokedex.data.local.entity.PokemonTeamEntity
import com.angga.pokedex.domain.model.PokemonTeam
import com.angga.pokedex.presentation.components.PokemonText
import com.angga.pokedex.presentation.team.PokemonTeamItem

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
        val list = repository.loadModel().collectAsState(initial = listOf(
            PokemonTeam(
            id = 1,
            name = "bulba",
            type2 = "",
            type1 = "aaaa"
        )
        )).value
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyColumn {
                itemsIndexed(
                    items = list,
                ) { index, pokemon ->
                    println("==== "+pokemon.getSpriteImageUrl().toUri())
                    Row {
                        Image(
                            provider = ImageProvider(pokemon.getSpriteImageUrl().toUri()),
                            contentDescription = pokemon.name)
                    }
                }
            }
        }
    }
}