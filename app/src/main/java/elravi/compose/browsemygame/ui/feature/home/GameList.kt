package elravi.compose.browsemygame.ui.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.components.*
import elravi.compose.browsemygame.ui.theme.BrowseMyGameTheme
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun GameListPreview() {
    BrowseMyGameTheme {
        GameListSection({})
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun GameListSection(
    navigateToDetailScreen: (Game) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val gameState by homeViewModel.newReleaseGameList.collectAsStateWithLifecycle()

    Column(modifier) {
        InfiniteGameList(
            gameState = gameState,
            getCurrentGameList = { homeViewModel.allGames },
            onLoadMore = { homeViewModel.getNewReleaseGame() },
            navigateToDetailScreen = navigateToDetailScreen
        )
    }
}