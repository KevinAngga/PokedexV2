package com.angga.pokedex.presentation.bottom_nav

import com.angga.pokedex.R

enum class BottomNavigation(
    val label : String,
    val icon : Int,
    val route: Any) {
    HOME("Pokedex", R.drawable.home, Destinations.Home),
    TEAMS("Team", R.drawable.ic_teams, Destinations.Team)
}