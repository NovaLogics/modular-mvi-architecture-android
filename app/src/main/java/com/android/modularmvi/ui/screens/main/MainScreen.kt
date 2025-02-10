package com.android.modularmvi.ui.screens.main

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.modularmvi.ui.navigation.AppNavigator
import com.android.modularmvi.ui.navigation.Destinations
import com.android.modularmvi.ui.navigation.Navigation
import com.android.modularmvi.ui.screens.example.DetailScreen
import com.android.modularmvi.ui.screens.example.ProfileScreen
import com.android.modularmvi.ui.screens.example.SettingScreen
import com.android.modularmvi.ui.screens.home.HomeScreen
import com.android.modularmvi.ui.screens.main.component.BottomNavigationBar
import com.android.modularmvi.ui.theme.ApplicationTheme
import com.android.modularmvi.util.MODE_LIGHT
import com.android.modularmvi.util.MODE_NIGHT

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    val appNavigator = AppNavigator(navController)
    val navRoutes = getNavigationRoutes()

    Scaffold(
        bottomBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            // Show bottom navigation bar only if the current route is in the main navigation list
            if (!currentRoute.isNullOrEmpty() && currentRoute in navRoutes.map { it.route }) {
                BottomNavigationBar(
                    appNavigator = appNavigator,
                    navItems = navRoutes,
                    currentRoute = currentRoute,
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            appNavigator = appNavigator,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Returns a list of main navigation destinations.
 */
@Composable
fun getNavigationRoutes() = listOf(
    Destinations.Home,
    Destinations.Profile,
    Destinations.Settings,
)

/**
 * Navigation Graph: Defines the app's navigation routes.
 */
@Composable
fun NavigationGraph(
    navController: NavHostController,
    appNavigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route,
        modifier = modifier
    ) {
        // Home Screen with navigation callback
        composable(Destinations.Home.route) {
            HomeScreen(
                onNavigateToDetail = { route ->
                    appNavigator.navigateTo(route)
                }
            )
        }

        // Profile and Settings Screens
        composable(Destinations.Profile.route) { ProfileScreen() }
        composable(Destinations.Settings.route) { SettingScreen() }

        // Detail Screen with dynamic item ID
        composable(
            route = Navigation.Routes.ITEM_DETAIL,
            arguments = listOf(navArgument(name = Navigation.Args.ITEM_ID){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Navigation.Args.ITEM_ID) ?: ""
            DetailScreen(itemId = id, navController)
        }
    }
}

@Preview(
    name = MODE_LIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = MODE_NIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun AppPreview() {
    ApplicationTheme {
        MainScreen()
    }
}

