package elravi.compose.browsemygame.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.UiState
import elravi.compose.browsemygame.ui.UiState.Companion.getErrorMessage
import elravi.compose.browsemygame.ui.theme.dimen0dp
import elravi.compose.browsemygame.ui.theme.dimen16dp
import elravi.compose.browsemygame.ui.theme.dimen20dp
import kotlinx.coroutines.flow.filter


@Composable
fun InfiniteGameList(
    gameState: UiState<List<Game>>,
    getCurrentGameList: () -> List<Game>,
    onLoadMore: () -> Unit,
    navigateToDetailScreen: (Game) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier
            .testTag(stringResource(id = R.string.tag_game_list))
            .fillMaxWidth()
            .fillMaxHeight(1.0f),
        verticalArrangement = Arrangement.spacedBy(dimen20dp),
        contentPadding = PaddingValues(dimen0dp, dimen0dp, dimen0dp, dimen16dp)
    ) {
        val gameList = getCurrentGameList()

        fun gameCards() {
            items(items = gameList, key = { item -> item.id }) { game ->
                GameCard(
                    game = game,
                    navigateToDetailScreen = navigateToDetailScreen,
                    onFavoriteButtonClick = { _, _ -> },
                    isShowFavoriteButton = false
                )
            }
        }

        when (gameState) {
            is UiState.Loading -> {
                if (gameList.isNotEmpty()) {
                    gameCards()
                    item {
                        CircleProgressBar(
                            loadingText = stringResource(R.string.text_loading_games)
                        )
                    }
                } else {
                    item {
                        Box(Modifier.fillParentMaxSize(1.0f)) {
                            CircleProgressBar(
                                loadingText = stringResource(R.string.text_loading_games),
                                Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
            is UiState.Success -> {
                items(items = gameState.data, key = { item -> item.id }) { game ->
                    GameCard(
                        game = game,
                        navigateToDetailScreen = navigateToDetailScreen,
                        onFavoriteButtonClick = { _, _ -> },
                        isShowFavoriteButton = false
                    )
                }
            }
            is UiState.Empty -> {
                if (gameList.isNotEmpty()) {
                    gameCards()
                    item {
                        BodyMediumText(
                            stringResource(R.string.text_end_of_game_list),
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = appTheme().onSurface
                        )
                    }
                } else {
                    item {
                        Box(Modifier.fillParentMaxSize(1.0f)) {
                            Retry(
                                content = {
                                    BodyMediumText(
                                        stringResource(R.string.text_games_not_available),
                                        color = appTheme().onSurface,
                                        textAlign = TextAlign.Center,
                                    )
                                },
                                onRetryClick = onLoadMore,
                                Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
            is UiState.Failure -> {
                if (gameList.isNotEmpty()) {
                    gameCards()
                    item {
                        Retry(
                            content = {
                                BodyMediumText(
                                    gameState.getErrorMessage(),
                                    color = appTheme().onSurface,
                                    textAlign = TextAlign.Center,
                                )
                            },
                            onRetryClick = onLoadMore,
                            Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    item {
                        Box(Modifier.fillParentMaxSize(1.0f)) {
                            Retry(
                                content = {
                                    BodyMediumText(
                                        gameState.getErrorMessage(),
                                        color = appTheme().onSurface,
                                        textAlign = TextAlign.Center,
                                    )
                                },
                                onRetryClick = onLoadMore,
                                Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
            else -> { return@LazyColumn }
        }
    }

    InfiniteListHandler(listState = listState, onLoadMore = onLoadMore)
}

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItems - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .filter { it }
            .collect {
                onLoadMore()
            }
    }
}