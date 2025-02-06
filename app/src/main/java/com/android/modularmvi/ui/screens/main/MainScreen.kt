package com.android.modularmvi.ui.screens.main

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.modularmvi.ui.navigation.Destinations
import com.android.modularmvi.ui.navigation.Routes
import com.android.modularmvi.ui.screens.main.component.BottomNavigationBar
import com.android.modularmvi.ui.theme.ApplicationTheme
import com.android.modularmvi.util.Constants


@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    val navRoutes = listOf(
        Destinations.Home,
        Destinations.Profile,
        Destinations.Settings,
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                navItems = navRoutes
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destinations.Home.route) { HomeScreen() }
            composable(Destinations.Profile.route) { ProfileScreen() }
            composable(Destinations.Settings.route) { SettingScreen() }
        }
    }
}

//Sample Screens
@Composable
fun HomeScreen() {
    Text(Routes.HOME, textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
}

@Composable
fun ProfileScreen() {
    Text(Routes.PROFILE, textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
}

@Composable
fun SettingScreen() {
    Text(Routes.SETTINGS, textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
}

@Preview(
    name = Constants.MODE_LIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = Constants.MODE_NIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun AppPreview() {
    ApplicationTheme {
        MainScreen()
    }
}
