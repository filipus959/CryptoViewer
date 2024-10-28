package com.filip.cryptoViewer.presentation.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Converters,
    )

    NavigationBar(
        modifier = Modifier.systemBarsPadding(),
    ) {
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentRoute = currentDestination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.label,
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
