package elravi.compose.browsemygame.ui.feature.gamesearch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.UiState
import elravi.compose.browsemygame.ui.components.*
import elravi.compose.browsemygame.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun GameSearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (Game) -> Unit,
    gameSearchViewModel: GameSearchViewModel = koinViewModel(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = appTheme().background)
            .padding(horizontal = dimen16dp, vertical = dimen0dp)
    ) {
        VerticalSpacer(height = dimen16dp)

        val gameState by gameSearchViewModel.gameSearchResult.collectAsStateWithLifecycle()
        var searchQuery by rememberSaveable { mutableStateOf("") }

        GameSearchTopAppBar(
            onBackButtonClick = { navController.navigateUp() },
            onSearchButtonClick = {
                if (it.isNotBlank()) {
                    searchQuery = it
                    gameSearchViewModel.startNewSearch(it)
                }
            }
        )
        if (gameState !is UiState.Default) {
            ContentSection(
                title = stringResource(R.string.title_search_result, "\"$searchQuery\""),
                content = {
                    Column(Modifier.padding(dimen0dp, dimen16dp, dimen0dp, dimen0dp)) {
                        InfiniteGameList(
                            gameState = gameState,
                            getCurrentGameList = { gameSearchViewModel.allGames },
                            onLoadMore = { gameSearchViewModel.searchGame(searchQuery) },
                            navigateToDetailScreen = navigateToDetailScreen
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameSearchTopAppBar(
    onBackButtonClick: () -> Unit,
    onSearchButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = dimen3dp,
                shape = RoundedCornerShape(dimen32dp),
                ambientColor = appTheme().background
            )
            .background(color = appTheme().surface)
            .padding(dimen16dp, dimen0dp),
    ) {
        val (backButton, queryInput, searchButton) = createRefs()

        Icon(
            painter = painterResource(id = R.drawable.ic_back_24),
            contentDescription = stringResource(id = R.string.desc_back_button),
            tint = appTheme().onSurface,
            modifier = Modifier
                .clickable { onBackButtonClick() }
                .constrainAs(backButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    height = Dimension.wrapContent
                }
        )

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        var searchQuery by rememberSaveable { mutableStateOf("") }

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            textStyle = MaterialTheme.typography.bodyMedium.copy(appTheme().primary),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = appTheme().surface,
                cursorColor = appTheme().onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = { BodyMediumText(text = stringResource(R.string.hint_type_your_game), color = appTheme().primary) },
            label = null,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearchButtonClick(searchQuery)
                }
            ),
            modifier = Modifier
                .constrainAs(queryInput) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(backButton.end, dimen4dp)
                    end.linkTo(searchButton.start, dimen4dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
                .focusRequester(focusRequester)
        )

        LaunchedEffect(Unit) {
            if (searchQuery.isBlank()) {
                focusRequester.requestFocus()
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_search_24),
            contentDescription = stringResource(id = R.string.desc_search_icon),
            tint = appTheme().onSurface,
            modifier = Modifier
                .clickable {
                    keyboardController?.hide()
                    focusRequester.freeFocus()
                    onSearchButtonClick(searchQuery)
                }
                .constrainAs(searchButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
        )
    }
}