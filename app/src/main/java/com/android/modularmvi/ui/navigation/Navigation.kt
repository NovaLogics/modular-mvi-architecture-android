package com.android.modularmvi.ui.navigation

import com.android.modularmvi.ui.navigation.Navigation.Args.ITEM_ID

object Navigation {
    object Args {
        const val ITEM_ID = "itemId"
        const val ITEM_JSON = "itemJson"
    }

    object Routes {
        const val HOME = "home"
        const val PROFILE = "profile"
        const val SETTINGS = "settings"
        const val DETAIL = "detail"
        const val ITEM_DETAIL = "$DETAIL/{$ITEM_ID}"
    }
}
