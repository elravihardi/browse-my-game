package elravi.compose.browsemygame

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import elravi.compose.browsemygame.ui.navigation.NavScreen
import elravi.compose.browsemygame.ui.theme.BrowseMyGameTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            BrowseMyGameTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                BrowseMyGameApp(navController = navController)
            }
        }
    }

    @Test
    fun nav_verify_start_destination() {
        navController.assertCurrentRouteName(NavScreen.Home.route)
    }

    @Test
    fun nav_verify_navigate_to_profile() {
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.desc_profile_pic))
            .performClick()
        navController.assertCurrentRouteName(NavScreen.Profile.route)
    }

    @Test
    fun nav_verify_navigate_to_favorite() {
        composeTestRule.onNodeWithContentDescription(composeTestRule.getString(R.string.desc_fav_icon))
            .performClick()
        navController.assertCurrentRouteName(NavScreen.FavoriteGameList.route)
    }

    @Test
    fun nav_verify_navigate_to_game_detail() {
        composeTestRule.waitUntil(30_000L) {
            composeTestRule
                .onAllNodes(hasTestTag(composeTestRule.getString(R.string.tag_game_card)))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodes(hasTestTag(composeTestRule.getString(R.string.tag_game_card)))[0].performClick()
        composeTestRule.waitForIdle()

        navController.assertCurrentRouteName(NavScreen.GameDetail.route)
    }

    @Test
    fun nav_verify_navigate_to_search() {
        with(composeTestRule) {
            onNodeWithTag(getString(R.string.tag_game_search_bar)).performClick()
        }
        navController.assertCurrentRouteName(NavScreen.GameSearch.route)
    }
}