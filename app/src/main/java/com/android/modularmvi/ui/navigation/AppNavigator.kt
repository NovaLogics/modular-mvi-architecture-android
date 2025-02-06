package com.android.modularmvi.ui.navigation

import androidx.navigation.NavController

class AppNavigator(private val navController: NavController) {

    fun navigateTo(destination: Destinations) {
        val route = when (destination) {
            is Destinations.Home -> destination.route
            is Destinations.Profile -> destination.route
            is Destinations.Settings -> destination.route
            is Destinations.Detail -> Destinations.Detail.createRoute(destination.itemId)
        }

        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            // Avoid building up a large stack of destinations
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }

    fun goBack() {
        navController.popBackStack()
    }
}
