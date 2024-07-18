package com.angga.pokedex.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.angga.pokedex.presentation.bottom_nav.BottomNavigation
import com.angga.pokedex.presentation.bottom_nav.Destinations
import com.angga.pokedex.presentation.components.MenuItem
import com.angga.pokedex.presentation.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavigation.HOME::class.qualifiedName.orEmpty()

                val isBottomAppBarVisible = rememberSaveable(navBackStackEntry) {
                    navBackStackEntry?.destination?.route == Destinations.Home::class.qualifiedName ||
                            navBackStackEntry?.destination?.route == Destinations.Team::class.qualifiedName
                }
                ChangeSystemBarsTheme(lightTheme = !isSystemInDarkTheme())
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    bottomBar = {
                      if (isBottomAppBarVisible) {
                          NavigationBar(
                              modifier = Modifier
                                  .padding(24.dp)
                                  .background(Color.White, CircleShape),
                              containerColor = Color.Transparent
                          ) {
                              Row(
                                  modifier = Modifier
                                      .fillMaxWidth()
                                      .padding(4.dp)
                                      .clip(RoundedCornerShape(34.dp)),
                                  verticalAlignment = Alignment.CenterVertically,
                                  horizontalArrangement = Arrangement.SpaceEvenly
                              ) {
                                  BottomNavigation.entries
                                      .forEachIndexed { index, bottomNavigation ->
                                          val isSelected by remember(currentRoute) {
                                              derivedStateOf {
                                                  currentRoute == bottomNavigation.route::class.qualifiedName
                                              }
                                          }

                                          MenuItem(
                                              modifier = Modifier
                                                  .weight(1f)
                                                  .align(Alignment.CenterVertically),
                                              label = bottomNavigation.label,
                                              drawableRes = bottomNavigation.icon,
                                              isSelected = isSelected,
                                              onClick = {
                                                  navController.navigate(bottomNavigation.route) {
                                                      popUpTo(navController.graph.findStartDestination().id)
                                                      launchSingleTop = true
                                                  }
                                              }
                                          )
                                      }
                              }
                          }
                      }
                    }
                ) { _ ->
                    NavigationRoot(navController = navController)
                }
            }
        }
    }
}


@Composable
private fun ComponentActivity.ChangeSystemBarsTheme(lightTheme: Boolean) {
    val barColor = Color.Transparent.toArgb()
    LaunchedEffect(lightTheme) {
        if (lightTheme) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    barColor, barColor,
                ),
                navigationBarStyle = SystemBarStyle.light(
                    barColor, barColor,
                ),
            )
        } else {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    barColor,
                ),
                navigationBarStyle = SystemBarStyle.dark(
                    barColor,
                ),
            )
        }
    }
}