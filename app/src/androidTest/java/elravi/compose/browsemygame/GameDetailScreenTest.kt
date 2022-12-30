package elravi.compose.browsemygame

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import elravi.compose.browsemygame.domain.gamedetail.model.response.EsrbRating
import elravi.compose.browsemygame.domain.gamedetail.model.response.GameDetail
import elravi.compose.browsemygame.domain.gamedetail.model.response.GamePoster
import elravi.compose.browsemygame.ui.UiState
import elravi.compose.browsemygame.ui.feature.gamedetail.GameDetailContent
import elravi.compose.browsemygame.ui.theme.BlueScoreColor
import elravi.compose.browsemygame.ui.theme.BrowseMyGameTheme
import elravi.compose.browsemygame.util.orDash
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GameDetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun generateFakeGameDetail(isFavorite: Boolean = true) = GameDetail(
        id = 32,
        slug = "elden-ring",
        title = "Elden Ring Game",
        released = "2022-02-25",
        updated = "",
        added = "",
        developers = "FromSoftwareDev",
        publishers = "Bandai Namco",
        metacritic = "77",
        metacriticColor = BlueScoreColor,
        rating = "4.4 / 5",
        playtime = "89",
        parentPlatforms = listOf(R.drawable.ic_windows, R.drawable.ic_playstation),
        platforms = "PC, Xbox One",
        description = "fake game desc about",
        gamePosterUrl = null,
        genres = "Action, RPG",
        addedByStatus = null,
        website = "https://gameku.com",
        suggestionsCount = "",
        stores = "steam, playstation",
        esrbRating = EsrbRating("Mature", 10, "ma"),
        isFavorite = isFavorite
    )

    @Test
    fun verify_game_detail_content_show() {
        composeTestRule.apply {
            val gameDetail = generateFakeGameDetail()
            val gamePosters = listOf(
                GamePoster("url1"), GamePoster("url2")
            )
            setContent {
                BrowseMyGameTheme {
                    GameDetailContent(
                        gameDetailState = UiState.success(gameDetail),
                        gamePosterState = UiState.success(gamePosters),
                        onBackButtonClicked = { },
                        onFavoriteButtonClicked = { _, _ -> },
                        {}, {}
                    )
                }
            }
            onNodeWithTag(getString(R.string.tag_game_poster)).assertExists()
            onNodeWithText(gameDetail.released).assertExists()
            onAllNodesWithContentDescription(getString(R.string.desc_platform_icon))
                .assertCountEquals(gameDetail.parentPlatforms.size)
            onNodeWithText(gameDetail.title).assertExists()
            onNodeWithText(activity.getString(R.string.text_average_playing_time_format, gameDetail.playtime)).assertExists()
            onNodeWithText(gameDetail.description).assertExists()
            onNodeWithText(gameDetail.esrbRating?.name.orDash()).assertExists()
            onNodeWithText(gameDetail.website).assertExists().assertHasClickAction()
            onNodeWithText(gameDetail.stores).assertExists()
            onNodeWithText(gameDetail.platforms).assertExists()
            onNodeWithText(gameDetail.metacritic).assertExists()
            onNodeWithText(gameDetail.rating).assertExists()
            onNodeWithText(gameDetail.genres).assertExists()
            onNodeWithText(gameDetail.publishers).assertExists()
            onNodeWithText(gameDetail.developers).assertExists()
        }
    }

    @Test
    fun verify_game_detail_loading_show() {
        composeTestRule.apply {
            setContent {
                BrowseMyGameTheme {
                    GameDetailContent(
                        gameDetailState = UiState.loading(),
                        gamePosterState = UiState.loading(),
                        onBackButtonClicked = { },
                        onFavoriteButtonClicked = { _, _ -> }, {}, {}
                    )
                }
            }
            onNodeWithText(getString(R.string.text_loading_game_detail)).assertExists()
            onNodeWithText(getString(R.string.text_loading_game_posters)).assertExists()
        }
    }

    @Test
    fun verify_game_detail_failure_show() {
        val msgErrorLoadGameDetail = "msgErrorLoadGameDetail"
        val msgErrorLoadGamePoster = "msgErrorLoadGamePoster"
        composeTestRule.apply {
            setContent {
                BrowseMyGameTheme {
                    GameDetailContent(
                        gameDetailState = UiState.fail(msgErrorLoadGameDetail),
                        gamePosterState = UiState.fail(msgErrorLoadGamePoster),
                        onBackButtonClicked = { },
                        onFavoriteButtonClicked = { _, _ -> }, {}, {}
                    )
                }
            }
            onNodeWithText(msgErrorLoadGameDetail).assertExists()
            onNodeWithText(msgErrorLoadGamePoster).assertExists()
        }
    }

    @Test
    fun verify_favorite_icon_click_work_when_is_favorite_true() {
        val gameDetail = generateFakeGameDetail(isFavorite = true)
        var favIconClickWork = false
        composeTestRule.apply {
            setContent {
                BrowseMyGameTheme {
                    GameDetailContent(
                        gameDetailState = UiState.success(gameDetail),
                        gamePosterState = UiState.loading(),
                        onBackButtonClicked = { },
                        onFavoriteButtonClicked = { currentFavoriteStatus, _ ->
                            favIconClickWork = currentFavoriteStatus
                        },
                        {}, {}
                    )
                }
            }
            onNodeWithTag(getString(R.string.tag_favorite_button))
                .assertExists()
                .assertHasClickAction()
                .performClick()
            assertTrue(favIconClickWork)
        }
    }

    @Test
    fun verify_favorite_icon_click_work_when_is_favorite_false() {
        val gameDetail = generateFakeGameDetail(isFavorite = false)
        var favIconClickWork = false
        composeTestRule.apply {
            setContent {
                BrowseMyGameTheme {
                    GameDetailContent(
                        gameDetailState = UiState.success(gameDetail),
                        gamePosterState = UiState.loading(),
                        onBackButtonClicked = { },
                        onFavoriteButtonClicked = { currentFavoriteStatus, _ ->
                            favIconClickWork = !currentFavoriteStatus
                        },
                        {}, {}
                    )
                }
            }
            onNodeWithTag(getString(R.string.tag_favorite_button))
                .assertExists()
                .assertHasClickAction()
                .performClick()
            assertTrue(favIconClickWork)
        }
    }
}