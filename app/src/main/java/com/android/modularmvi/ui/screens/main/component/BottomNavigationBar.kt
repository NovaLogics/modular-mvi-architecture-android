package com.android.modularmvi.ui.screens.main.component

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.modularmvi.R
import com.android.modularmvi.ui.navigation.AppNavigator
import com.android.modularmvi.ui.navigation.Destinations

import java.util.Locale


@Composable
fun BottomNavigationBar(
    navController: NavController,
    navItems: List<Destinations>
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val appNavigator = AppNavigator(navController)

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        navItems.forEachIndexed { index, screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                onClick = { appNavigator.navigateTo(screen) },
                label = { NavigationLabel(screenName = screen.route) },
                icon = { NavigationIcon(navItemIndex = index) },
            )
        }
    }
}

@Composable
fun NavigationLabel(screenName: String) {
    Text(
        screenName.uppercase(Locale.getDefault()),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Composable
fun NavigationIcon(navItemIndex: Int) {
    val iconRes = when (navItemIndex) {
        0 -> R.drawable.ic_nav_home
        1 -> R.drawable.ic_nav_profile
        2 -> R.drawable.ic_nav_settings
        else -> R.drawable.ic_nav_home
    }
    return Icon(
        painter = painterResource(id = iconRes),
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = null
    )
}
