package com.android.modularmvi.ui.navigation

sealed class Destinations(val route: String) {
    data object Home : Destinations(Routes.HOME)
    data object Profile : Destinations(Routes.PROFILE)
    data object Settings : Destinations(Routes.SETTINGS)

    data class Detail(val itemId: String) : Destinations("${Routes.DETAIL}/$itemId") {
        companion object {
            fun createRoute(itemId: String) = "${Routes.DETAIL}/$itemId"
        }
    }
}
