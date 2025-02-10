package com.android.modularmvi.ui.navigation

sealed class Destinations(val route: String) {
    data object Home : Destinations(Navigation.Routes.HOME)
    data object Profile : Destinations(Navigation.Routes.PROFILE)
    data object Settings : Destinations(Navigation.Routes.SETTINGS)

    data class Detail(val itemId: String) : Destinations("${Navigation.Routes.DETAIL}/$itemId") {
        companion object {
            fun createRoute(itemId: String) = "${Navigation.Routes.DETAIL}/$itemId"
        }
    }
}
