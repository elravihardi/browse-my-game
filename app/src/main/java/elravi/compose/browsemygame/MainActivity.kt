package elravi.compose.browsemygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import elravi.compose.browsemygame.ui.feature.favorite.FavoriteGameScreen
import elravi.compose.browsemygame.ui.feature.gamedetail.GameDetailScreen
import elravi.compose.browsemygame.ui.feature.gamesearch.GameSearchScreen
import elravi.compose.browsemygame.ui.feature.home.HomeScreen
import elravi.compose.browsemygame.ui.feature.profile.ProfileScreen
import elravi.compose.browsemygame.ui.navigation.NavScreen
import elravi.compose.browsemygame.ui.theme.BrowseMyGameTheme
import elravi.compose.browsemygame.util.orZero
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrowseMyGameTheme {
                BrowseMyGameApp()
            }
        }
    }
}

@Composable
fun BrowseMyGameApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavScreen.Home.route
) {
    // we use the same LocalViewModelStoreOwner for favorite & game detail screen
    // because we want to share HomeViewModel between those 2 screen
    val favoriteViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(
        modifier = modifier.background(appTheme().background),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavScreen.Home.route) {
            HomeScreen(navController, navigateToDetailScreen = { game ->
                navController.navigate(
                    NavScreen.GameDetail.navArgsRoute(NavScreen.Home.route, game.id)
                )
            })
        }
        composable(NavScreen.FavoriteGameList.route) {
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides favoriteViewModelStoreOwner
            ) {
                FavoriteGameScreen(navController, navigateToDetailScreen = { game ->
                    navController.navigate(
                        NavScreen.GameDetail.navArgsRoute(NavScreen.FavoriteGameList.route, game.id)
                    )
                })
            }
        }
        composable(NavScreen.GameSearch.route) {
            GameSearchScreen(navController, navigateToDetailScreen = { game ->
                navController.navigate(
                    NavScreen.GameDetail.navArgsRoute(NavScreen.GameSearch.route, game.id)
                )
            })
        }
        composable(NavScreen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(NavScreen.GameDetail.route, NavScreen.GameDetail.getArguments()) {
            val args = it.arguments
            val fromScreen = args?.getString(NavScreen.GameDetail.fromScreenKey).orEmpty()
            if (fromScreen == NavScreen.FavoriteGameList.route) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides favoriteViewModelStoreOwner
                ) {
                    GameDetailScreen(
                        gameId = args?.getInt(NavScreen.GameDetail.gameIdKey).orZero(),
                        navController = navController,
                        favoriteGameViewModel = koinViewModel(),
                    )
                }
            } else {
                GameDetailScreen(
                    gameId = args?.getInt(NavScreen.GameDetail.gameIdKey).orZero(),
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun appTheme() = MaterialTheme.colorScheme

fun NavOptionsBuilder.defaultNavOptionsBuilder(navController: NavController) {
    popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
    }
    restoreState = true
    launchSingleTop = true
}