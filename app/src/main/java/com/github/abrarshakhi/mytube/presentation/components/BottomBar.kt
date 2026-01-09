package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.abrarshakhi.mytube.presentation.navigation.Routes

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        Routes.HomeBottom.Channel,
        Routes.HomeBottom.Video
    )

    NavigationBar {
        val currentRoute =
            navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text(screen.route) }
            )
        }
    }
}
