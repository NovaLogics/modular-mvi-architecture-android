package com.android.modularmvi.ui.navigation

import androidx.navigation.NavController

class AppNavigator(
    private val navController: NavController
) {
    /**
     * Navigates to the specified destination.
     */
    fun navigateTo(destination: Destinations) {
        val route = when (destination) {
            is Destinations.Detail -> Destinations.Detail.createRoute(destination.itemId)
            else -> destination.route
        }
        navigate(route)
    }

    /**
     * Handles navigation while managing the back stack efficiently.
     */
    private fun navigate(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true  // Prevent multiple instances of the same screen
            restoreState = true  // Preserve state when re-selecting the same destination
        }
    }

    /**
     * Navigates back if possible.
     */
    fun goBack() {
        if (!navController.popBackStack()) {
            // Handle additional back navigation logic if needed
        }
    }
}

