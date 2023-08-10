package elravi.compose.browsemygame.ui.feature.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.UiState
import elravi.compose.browsemygame.ui.UiState.Companion.getErrorMessage
import elravi.compose.browsemygame.ui.UiState.Companion.getSuccessData
import elravi.compose.browsemygame.ui.components.BodyMediumText
import elravi.compose.browsemygame.ui.components.CircleProgressBar
import elravi.compose.browsemygame.ui.components.GameCard
import elravi.compose.browsemygame.ui.components.Retry
import elravi.compose.browsemygame.ui.theme.dimen0dp
import elravi.compose.browsemygame.ui.theme.dimen16dp
import elravi.compose.browsemygame.ui.theme.dimen20dp
import elravi.compose.browsemygame.util.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteGameListSection(
    navigateToDetailScreen: (Game) -> Unit,
    modifier: Modifier = Modifier,
    favoriteGameViewModel: FavoriteGameViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val favGameState by favoriteGameViewModel.allFavoriteGameState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(true) {
        favoriteGameViewModel.getAllFavoriteGame()
    }

    Column(modifier) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1.0f),
            verticalArrangement = Arrangement.spacedBy(dimen20dp),
            contentPadding = PaddingValues(dimen0dp, dimen0dp, dimen0dp, dimen16dp)
        ) {
            when (favGameState) {
                is UiState.Loading -> {
                    item {
                        Box(Modifier.fillParentMaxSize(1.0f)) {
                            CircleProgressBar(
                                loadingText = stringResource(R.string.text_loading_fav_games),
                                Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                is UiState.Success -> {
                    favGameState.getSuccessData()?.let {
                        items(items = it, key = { item -> item.id }) { game ->
                            GameCard(
                                game = game,
                                navigateToDetailScreen = navigateToDetailScreen,
                                onFavoriteButtonClick = { _, isFavorite ->
                                    if (isFavorite) {
                                        favoriteGameViewModel.removeFavGame(game)
                                    }
                                },
                                isShowFavoriteButton = true
                            )
                        }
                    }
                }
                is UiState.Empty -> {
                    item {
                        Box(Modifier.fillParentMaxSize(1.0f)) {
                            BodyMediumText(
                                stringResource(R.string.text_fav_games_not_available),
                                Modifier.align(Alignment.Center),
                                color = appTheme().onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is UiState.Failure -> {
                    item {
                        Box(Modifier.fillParentMaxSize(1.0f)) {
                            Retry(
                                content = {
                                    BodyMediumText(
                                        favGameState.getErrorMessage(),
                                        color = appTheme().onSurface,
                                        textAlign = TextAlign.Center,
                                    )
                                },
                                onRetryClick = { favoriteGameViewModel.getAllFavoriteGame() },
                                Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                else -> { return@LazyColumn }
            }
        }
    }

    val removeGameStatus by favoriteGameViewModel.removeFavGameState.collectAsStateWithLifecycle()
    removeGameStatus.getContentIfNotHandled()?.let {
        when (it) {
            is UiState.Success -> {
                showToast(context, stringResource(
                        R.string.message_action_success_favorite_game_format,
                        stringResource(id = R.string.action_remove)
                    )
                )
                favoriteGameViewModel.getAllFavoriteGame()
            }
            is UiState.Failure -> {
                showToast(context,
                    stringResource(
                        R.string.message_action_failed_favorite_game_format,
                        stringResource(id = R.string.action_remove)
                    )
                )
            }
            else -> { return@let }
        }
    }
}