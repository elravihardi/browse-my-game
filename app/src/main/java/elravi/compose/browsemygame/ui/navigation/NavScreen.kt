package elravi.compose.browsemygame.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavScreen(val route: String) {
    object Home : NavScreen("home")
    object Profile : NavScreen("profile")
    object FavoriteGameList : NavScreen("favoriteList")
    object GameDetail : NavScreen("gameDetail/{fromScreen}/{gameId}") {
        const val fromScreenKey = "fromScreen"
        const val gameIdKey = "gameId"

        fun getArguments() = listOf(
            navArgument(fromScreenKey) {
                type = NavType.StringType
            },
            navArgument(gameIdKey) {
                type = NavType.IntType
            },
        )
        fun navArgsRoute(fromScreen: String, gameId: Int) =
            "gameDetail/$fromScreen/$gameId"
    }
    object GameSearch : NavScreen("gameSearch")
}
