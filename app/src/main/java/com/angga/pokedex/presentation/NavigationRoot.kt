package com.angga.pokedex.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.angga.pokedex.presentation.bottom_nav.Destinations
import com.angga.pokedex.presentation.detail.PokemonDetailScreenRoot
import com.angga.pokedex.presentation.list.PokemonListScreen
import com.angga.pokedex.presentation.team.PokemonTeamScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.BottomNavGraph
    ) {
        mainGraph(
            navigateToDetail = { pokemonId, fromTeamPage ->
                navController.navigate(Destinations.Detail(pokemonId, fromTeamPage))
            }
        )


        /** use primitive **/
        composable<Destinations.Detail> {
            PokemonDetailScreenRoot()
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
    navigateToDetail : (Int, Boolean) -> Unit,
) {
    navigation<Destinations.BottomNavGraph>(
        startDestination = Destinations.Home
    ) {
        composable<Destinations.Home> {
            PokemonListScreen(
                navigateToDetailPage = {
                    navigateToDetail(it, false)
                }
            )
        }

        composable<Destinations.Team> {
            PokemonTeamScreenRoot(
                navigateToDetailPage = {
                    navigateToDetail(it, true)
                }
            )
        }
    }
}

