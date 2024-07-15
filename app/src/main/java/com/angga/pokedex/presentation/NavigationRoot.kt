package com.angga.pokedex.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.angga.pokedex.presentation.bottom_nav.Destinations
import com.angga.pokedex.presentation.detail.PokemonDetailScreen
import com.angga.pokedex.presentation.list.PokemonListScreen
import com.angga.pokedex.presentation.team.PokemonTeam

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.BottomNavGraph
    ) {
        mainGraph(navController)
        detailGraph()
    }
}

private fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation<Destinations.BottomNavGraph>(
        startDestination = Destinations.Home
    ) {
        composable<Destinations.Home> {
            PokemonListScreen(
                navigateToDetailPage = {
                    navController.navigate(Destinations.DetailGraph)
                }
            )
        }

        composable<Destinations.Team> {
            PokemonTeam()
        }
    }
}

private fun NavGraphBuilder.detailGraph() {
    navigation<Destinations.DetailGraph>(
        startDestination = Destinations.Detail
    ) {
        composable<Destinations.Detail> {
            PokemonDetailScreen()
        }
    }
}