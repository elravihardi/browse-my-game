package elravi.compose.browsemygame.ui.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import elravi.compose.browsemygame.BrowseMyGameApp
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.defaultNavOptionsBuilder
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.components.BodyMediumText
import elravi.compose.browsemygame.ui.components.ContentSection
import elravi.compose.browsemygame.ui.components.FavoriteIcon
import elravi.compose.browsemygame.ui.components.ProfilePicture
import elravi.compose.browsemygame.ui.navigation.NavScreen
import elravi.compose.browsemygame.ui.theme.*

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DefaultPreviews() {
    BrowseMyGameTheme {
        BrowseMyGameApp()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultDarkPreviews() {
    BrowseMyGameTheme {
        BrowseMyGameApp()
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (Game) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = appTheme().background)
            .padding(horizontal = dimen16dp, vertical = dimen0dp)
    ) {
        HomeTopAppBar(
            onFavoriteIconClick = {
                navController.navigate(NavScreen.FavoriteGameList.route) {
                    defaultNavOptionsBuilder(navController)
                }
            },
            onProfilePicClick = {
                navController.navigate(NavScreen.Profile.route) {
                    defaultNavOptionsBuilder(navController)
                }
            },
            onSearchTextClick = {
                navController.navigate(NavScreen.GameSearch.route) {
                    defaultNavOptionsBuilder(navController)
                }
            },
            Modifier.padding(dimen0dp, dimen16dp, dimen0dp, dimen0dp)
        )
        ContentSection(
            title = stringResource(R.string.title_new_trending_games),
            content = {
                GameListSection(
                    navigateToDetailScreen,
                    Modifier.padding(dimen0dp, dimen16dp, dimen0dp, dimen0dp)
                )
            }
        )
    }
}

@Composable
fun HomeTopAppBar(
    onFavoriteIconClick: () -> Unit,
    onProfilePicClick: () -> Unit,
    onSearchTextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .testTag(stringResource(id = R.string.tag_game_search_bar))
            .fillMaxWidth()
            .shadow(
                elevation = dimen3dp,
                shape = RoundedCornerShape(dimen32dp),
                ambientColor = appTheme().background
            )
            .background(color = appTheme().surface)
            .clickable { onSearchTextClick() }
            .padding(horizontal = dimen16dp, vertical = dimen12dp),
        horizontalArrangement = Arrangement.Start
    ) {
        FavoriteIcon(onClick = onFavoriteIconClick, iconSize = dimen28dp)

        Spacer(modifier = Modifier.size(dimen16dp))

        BodyMediumText(
            text = stringResource(R.string.label_search_your_games),
            color = appTheme().onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ProfilePicture(
                R.drawable.img_profile_picture,
                onClick = onProfilePicClick
            )
        }
    }
}