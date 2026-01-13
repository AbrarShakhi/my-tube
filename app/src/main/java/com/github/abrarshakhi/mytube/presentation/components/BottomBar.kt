package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.abrarshakhi.mytube.presentation.navigation.Routes

data class BottomBarItem(
    val routes: Routes,
    val icon: ImageVector,
)

@Composable
fun BottomBar(navController: NavController) {
    val items = remember {
        listOf(
            BottomBarItem(routes = Routes.Home.Video, icon = Icons.Default.VideoLibrary),
            BottomBarItem(routes = Routes.Home.Channel, icon = Icons.Default.Person)
        )
    }

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.routes.route,
                onClick = {
                    navController.navigate(item.routes.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.routes.route) },
                label = { Text(item.routes.route) })
        }
    }
}
