package elravi.compose.browsemygame.ui.feature.gamedetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.domain.gamedetail.model.response.GameDetail
import elravi.compose.browsemygame.domain.gamedetail.model.response.GamePoster
import elravi.compose.browsemygame.ui.UiState
import elravi.compose.browsemygame.ui.UiState.Companion.checkState
import elravi.compose.browsemygame.ui.components.*
import elravi.compose.browsemygame.ui.feature.favorite.FavoriteGameViewModel
import elravi.compose.browsemygame.ui.theme.*
import elravi.compose.browsemygame.util.orDash
import elravi.compose.browsemygame.util.showToast
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun GameDetailScreen(
    gameId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    gameDetailViewModel: GameDetailViewModel = koinViewModel(),
    favoriteGameViewModel: FavoriteGameViewModel? = null,
) {
    val context = LocalContext.current
    val gameDetailState by gameDetailViewModel.gameDetailState.collectAsStateWithLifecycle()
    val gamePosterState by gameDetailViewModel.gamePosterState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        if (gameDetailState is UiState.Default) {
            gameDetailViewModel.getGameDetail(gameId)
        }
        if (gamePosterState is UiState.Default) {
            gameDetailViewModel.getGamePoster(gameId)
        }
    }

    GameDetailContent(
        gameDetailState = gameDetailState,
        gamePosterState = gamePosterState,
        onBackButtonClicked = { navController.navigateUp() },
        onFavoriteButtonClicked = { currentChecked, gameDetail ->
            if (currentChecked) {
                gameDetailViewModel.removeFavGame(gameDetail.id)
            } else {
                gameDetailViewModel.addFavGame(gameDetail)
            }
        },
        onRetryLoadGameDetail = { gameDetailViewModel.getGameDetail(gameId) },
        onRetryLoadGamePoster = { gameDetailViewModel.getGamePoster(gameId) },
        modifier
    )

    val addFavGameStatus by gameDetailViewModel.addFavGameState.collectAsStateWithLifecycle()
    addFavGameStatus.getContentIfNotHandled()?.let {
        when (it) {
            is UiState.Success -> {
                showToast(context,
                    stringResource(
                        R.string.message_action_success_favorite_game_format,
                        stringResource(id = R.string.action_add)
                    )
                )
                gameDetailViewModel.updateGameDetailFavoriteStatus(true)
            }
            is UiState.Failure -> {
                showToast(context,
                    stringResource(
                        R.string.message_action_failed_favorite_game_format,
                        stringResource(id = R.string.action_add)
                    )
                )
            }
            else -> { return@let }
        }
    }

    val removeFavGameStatus by gameDetailViewModel.removeFavGameState.collectAsStateWithLifecycle()
    removeFavGameStatus.getContentIfNotHandled()?.let {
        when (it) {
            is UiState.Success -> {
                showToast(context,
                    stringResource(
                        R.string.message_action_success_favorite_game_format,
                        stringResource(id = R.string.action_remove)
                    )
                )
                favoriteGameViewModel?.getAllFavoriteGame()
                gameDetailViewModel.updateGameDetailFavoriteStatus(false)
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

@Composable
fun GameDetailContent(
    gameDetailState: UiState<GameDetail>,
    gamePosterState: UiState<List<GamePoster>>,
    onBackButtonClicked: () -> Unit,
    onFavoriteButtonClicked: (Boolean, GameDetail) -> Unit,
    onRetryLoadGameDetail: () -> Unit,
    onRetryLoadGamePoster: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val (gamePosterCarousel, backButton, favoriteButton, contentContainer) = createRefs()

        GamePosterCarousel(
            gamePosterState,
            onRetryLoadGamePoster = onRetryLoadGamePoster,
            Modifier.constrainAs(gamePosterCarousel) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        val (iconButtonBgColor, iconButtonShape) = Pair(appTheme().surface.copy(alpha = 0.5f), CircleShape)
        ClickableIconButton(
            icon = R.drawable.ic_back_24,
            contentDesc = stringResource(R.string.desc_back_button),
            iconColorTint = appTheme().onSurface,
            onClick = onBackButtonClicked,
            modifier = Modifier
                .constrainAs(backButton) {
                    top.linkTo(parent.top, dimen24dp)
                    start.linkTo(parent.start, dimen16dp)
                }
                .size(dimen40dp)
                .background(iconButtonBgColor, iconButtonShape)
                .padding(dimen4dp)
        )

        gameDetailState.checkState(
            onLoading = {
                val pbRef = createRef()
                CircleProgressBar(loadingText = stringResource(R.string.text_loading_game_detail), Modifier.constrainAs(pbRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                })
            },
            onSuccess = { gameDetail ->
                ClickableCheckedIconButton(
                    isCheck = gameDetail.isFavorite,
                    onClick = { onFavoriteButtonClicked(it, gameDetail) },
                    modifier = Modifier
                        .testTag(stringResource(id = R.string.tag_favorite_button))
                        .constrainAs(favoriteButton) {
                            top.linkTo(backButton.top)
                            end.linkTo(parent.end, dimen16dp)
                        }
                        .size(dimen40dp)
                        .background(iconButtonBgColor, iconButtonShape)
                        .padding(dimen4dp),
                ) { isCheck, modifier ->
                    FavoriteIcon(
                        iconSize = dimen24dp,
                        isFilled = isCheck,
                        modifier = modifier
                    )
                }

                GameDescriptionContent(
                    gameDetail = gameDetail,
                    modifier = Modifier.constrainAs(contentContainer) {
                        top.linkTo(gamePosterCarousel.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )
            },
            onFailure = { _, errorMessage ->
                Retry(
                    content = {
                        BodyMediumText(
                            errorMessage,
                            color = appTheme().onSurface,
                            textAlign = TextAlign.Center,
                        )
                    },
                    onRetryClick = onRetryLoadGameDetail,
                    Modifier.constrainAs(createRef()) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, dimen24dp)
                        end.linkTo(parent.end, dimen24dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun GameDescriptionContent(
    gameDetail: GameDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(dimen24dp, dimen0dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(height = dimen24dp)
        // Release date
        TitleSmallText(
            text = gameDetail.released,
            modifier = Modifier
                .background(appTheme().onSurface, RoundedCornerShape(dimen8dp))
                .padding(dimen8dp, dimen6dp),
            color = appTheme().surface
        )

        VerticalSpacer(height = dimen16dp)
        // Platform icons
        Row(horizontalArrangement = Arrangement.spacedBy(dimen8dp)) {
            gameDetail.parentPlatforms.forEach {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = stringResource(id = R.string.desc_platform_icon),
                    Modifier.size(dimen28dp),
                    colorFilter = ColorFilter.tint(appTheme().onSurface)
                )
            }
        }

        VerticalSpacer(height = dimen16dp)
        // Average play time
        BodyMediumText(
            text = stringResource(id = R.string.text_average_playing_time_format, gameDetail.playtime),
            color = appTheme().onSurface,
        )

        VerticalSpacer(height = dimen16dp)
        // Game title
        HeadlineMediumText(
            text = gameDetail.title,
            color = appTheme().primary,
            textAlign = TextAlign.Center
        )

        @Composable
        fun generateSubContent(labelId: Int, content: String, isContentHyperlink: Boolean = false) {
            VerticalSpacer(height = dimen16dp)
            // label
            TitleSmallText(
                text = stringResource(labelId),
                modifier = Modifier.fillMaxWidth(),
                color = appTheme().primary
            )

            VerticalSpacer(height = dimen8dp)
            // content
            if (isContentHyperlink) {
                val uriHandler = LocalUriHandler.current
                BodyMediumText(
                    text = content,
                    color = appTheme().onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uriHandler.openUri(content) },
                    lineHeight = TextUnit(1.5f, TextUnitType.Em)
                )
            } else {
                BodyMediumText(
                    text = content,
                    color = appTheme().onSurface,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = TextUnit(1.5f, TextUnitType.Em)
                )
            }
        }

        VerticalSpacer(height = dimen16dp)
        generateSubContent(labelId = R.string.label_about_game, content = gameDetail.description)

        VerticalSpacer(height = dimen16dp)
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.weight(0.5f)) {
                val itemList = listOf(
                    Pair(R.string.label_platforms_game, gameDetail.platforms),
                    Pair(R.string.label_genre_game, gameDetail.genres),
                    Pair(R.string.label_developer_game, gameDetail.developers),
                    Pair(R.string.label_age_rating_game, gameDetail.esrbRating?.name.orDash()),
                )
                itemList.forEach {
                    generateSubContent(labelId = it.first, content = it.second)
                }
            }
            HorizontalSpacer(width = dimen8dp)
            Column(Modifier.weight(0.5f)) {
                VerticalSpacer(height = dimen16dp)
                // metascore label
                TitleSmallText(
                    text = stringResource(R.string.label_metascore_game),
                    modifier = Modifier.align(Alignment.Start),
                    color = appTheme().primary
                )
                VerticalSpacer(height = dimen8dp)
                // metascore game
                TitleMediumText(
                    text = gameDetail.metacritic,
                    color = gameDetail.metacriticColor
                )

                val itemList = listOf(
                    Pair(R.string.label_rating_game, gameDetail.rating),
                    Pair(R.string.label_publisher_game, gameDetail.publishers),
                    Pair(R.string.label_website_game, gameDetail.website),
                )
                itemList.forEach {
                    generateSubContent(
                        labelId = it.first,
                        content = it.second,
                        (it.first == R.string.label_website_game)
                    )
                }
            }
        }

        VerticalSpacer(height = dimen16dp)
        generateSubContent(labelId = R.string.label_where_to_buy_game, content = gameDetail.stores)
        VerticalSpacer(height = dimen16dp)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GamePosterCarousel(
    gamePosterState: UiState<List<GamePoster>>,
    onRetryLoadGamePoster: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()

    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .height(dimen300dp)
    ) {
        gamePosterState.checkState(
            onLoading = {
                CircleProgressBar(
                    loadingText = stringResource(R.string.text_loading_game_posters),
                    Modifier.constrainAs(createRef()) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
                )
            },
            onSuccess = { gamePosterUrlList ->
                val gamePosterPager = createRef()

                HorizontalPager(
                    count = gamePosterUrlList.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .constrainAs(gamePosterPager) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        },
                ) { page ->
                    ConstraintLayout(
                        Modifier
                            .fillMaxSize()
                            .testTag(stringResource(R.string.tag_game_poster))) {
                        var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }
                        AsyncImage(
                            model = gamePosterUrlList[page].imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .constrainAs(createRef()) {
                                    width = Dimension.matchParent
                                    height = Dimension.matchParent
                                },
                            onState = { imageState = it },
                        )
                        val placeholderId = when (imageState) {
                            is AsyncImagePainter.State.Loading -> R.drawable.ic_downloading_24
                            is AsyncImagePainter.State.Error -> R.drawable.ic_broken_image_24
                            else -> null
                        }
                        placeholderId?.let {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(dimen64dp)
                                    .constrainAs(createRef()) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                        width = Dimension.wrapContent
                                    }
                            )
                        }
                    }
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = LightGray,
                    modifier = Modifier.constrainAs(createRef()) {
                        start.linkTo(gamePosterPager.start)
                        end.linkTo(gamePosterPager.end)
                        bottom.linkTo(gamePosterPager.bottom, dimen16dp)
                        width = Dimension.wrapContent
                    },
                )
            },
            onFailure = { _, errorMsg ->
                Retry(
                    content = {
                        BodyMediumText(
                            errorMsg,
                            color = appTheme().onSurface,
                            textAlign = TextAlign.Center,
                        )
                    },
                    onRetryClick = onRetryLoadGamePoster,
                    Modifier.constrainAs(createRef()) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, dimen24dp)
                        end.linkTo(parent.end, dimen24dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
                )
            }
        )
    }
}