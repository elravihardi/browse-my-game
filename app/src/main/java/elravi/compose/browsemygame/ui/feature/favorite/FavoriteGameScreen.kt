package elravi.compose.browsemygame.ui.feature.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.components.ContentSection
import elravi.compose.browsemygame.ui.components.VerticalSpacer
import elravi.compose.browsemygame.ui.theme.dimen0dp
import elravi.compose.browsemygame.ui.theme.dimen16dp
import elravi.compose.browsemygame.ui.theme.dimen24dp

@Composable
fun FavoriteGameScreen(
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
        VerticalSpacer(height = dimen24dp)
        Icon(
            painter = painterResource(id = R.drawable.ic_back_24),
            contentDescription = stringResource(R.string.desc_back_button),
            tint = appTheme().onSurface,
            modifier = Modifier.clickable { navController.navigateUp() }
        )
        ContentSection(
            title = stringResource(R.string.title_your_favorite_game),
            content = {
                FavoriteGameListSection(
                    navigateToDetailScreen,
                    Modifier.padding(dimen0dp, dimen16dp, dimen0dp, dimen0dp)
                )
            }
        )
    }
}