package com.angga.pokedex.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
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
        mainGraph(
            navigateToDetail = {
                navController.navigate(Destinations.Detail(it))
            }
        )


        /** use primitive **/
        composable<Destinations.Detail> {backStackEntry ->
            val id = backStackEntry.toRoute<Destinations.Detail>().pokemonId
            PokemonDetailScreen(id)
        }

        /** use object **/
//        composable<Destinations.Detail>(
//            typeMap = mapOf(typeOf<Pokemon>() to serializableType<Pokemon>())
//        ) {backStackEntry ->
//            val pokemon = backStackEntry.toRoute<Destinations.Detail>().pokemon
//            PokemonDetailScreen(pokemon)
//        }
    }
}

private fun NavGraphBuilder.mainGraph(
    navigateToDetail : (Int) -> Unit,
) {
    navigation<Destinations.BottomNavGraph>(
        startDestination = Destinations.Home
    ) {
        composable<Destinations.Home> {
            PokemonListScreen(
                navigateToDetailPage = {
                    navigateToDetail(it)
                }
            )
        }

        composable<Destinations.Team> {
            PokemonTeam()
        }
    }
}

