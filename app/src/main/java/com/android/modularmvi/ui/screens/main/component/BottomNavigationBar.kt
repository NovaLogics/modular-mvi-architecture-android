package com.android.modularmvi.ui.screens.main.component

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.android.modularmvi.R
import com.android.modularmvi.ui.navigation.AppNavigator
import com.android.modularmvi.ui.navigation.Destinations
import java.util.Locale

@Composable
fun BottomNavigationBar(
    appNavigator: AppNavigator,
    navItems: List<Destinations>,
    currentRoute: String
) {
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

/**
 * Displays a label for the navigation item.
 */
@Composable
fun NavigationLabel(screenName: String) {
    Text(
        text = screenName.uppercase(Locale.getDefault()),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

/**
 * Displays an icon for the navigation item.
 */
@Composable
fun NavigationIcon(navItemIndex: Int) {
    // Map each index to an appropriate icon resource
    val iconRes = when (navItemIndex) {
        0 -> R.drawable.ic_nav_home
        1 -> R.drawable.ic_nav_profile
        2 -> R.drawable.ic_nav_settings
        else -> R.drawable.ic_nav_home // Default fallback
    }

    Icon(
        painter = painterResource(id = iconRes),
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = null
    )
}

