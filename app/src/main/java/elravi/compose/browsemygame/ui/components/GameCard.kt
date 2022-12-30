package elravi.compose.browsemygame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.theme.*

@Composable
fun GameCard(
    game: Game,
    navigateToDetailScreen: (Game) -> Unit,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: (Game, Boolean) -> Unit,
    isShowFavoriteButton: Boolean
) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .shadow(
                elevation = dimen3dp,
                shape = RoundedCornerShape(dimen12dp),
                ambientColor = appTheme().background
            )
            .background(color = appTheme().surface)
            .clickable { navigateToDetailScreen(game) }
            .padding(dimen0dp, dimen0dp, dimen0dp, dimen12dp)
            .testTag(stringResource(id = R.string.tag_game_card))
    ) {
        val (gameImage, scoreText, platformRow, favIcon, titleText, releaseDateText) = createRefs()
        GameImage(
            imageUrl = game.gamePosterUrl.orEmpty(),
            modifier = Modifier.constrainAs(gameImage) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        TitleMediumText(
            text = game.metacritic,
            modifier = Modifier.constrainAs(scoreText) {
                top.linkTo(gameImage.bottom, dimen12dp)
                start.linkTo(parent.start, dimen16dp)
            },
            color = game.metacriticColor
        )
        Row(
            Modifier.constrainAs(platformRow) {
                top.linkTo(scoreText.top)
                bottom.linkTo(scoreText.bottom)
                start.linkTo(scoreText.end, dimen16dp)
                end.linkTo(favIcon.start, dimen16dp)
                width = Dimension.fillToConstraints
            },
            horizontalArrangement = Arrangement.spacedBy(dimen8dp)
        ) {
            game.platforms.forEach { 
                Image(
                    painter = painterResource(id = it), 
                    contentDescription = null,
                    Modifier.size(dimen20dp),
                    colorFilter = ColorFilter.tint(appTheme().onSurface)
                )
            }
        }
        if (isShowFavoriteButton) {
            FavoriteIcon(
                onClick = { onFavoriteButtonClick(game, game.isFavorite) },
                iconSize = dimen24dp,
                modifier = Modifier.constrainAs(favIcon) {
                    top.linkTo(scoreText.top)
                    bottom.linkTo(scoreText.bottom)
                    end.linkTo(parent.end, dimen16dp)
                },
                isFilled = game.isFavorite
            )
        }
        TitleMediumText(
            text = game.title,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(scoreText.bottom, dimen8dp)
                    start.linkTo(parent.start, dimen16dp)
                    end.linkTo(parent.end, dimen16dp)
                    width = Dimension.fillToConstraints
                },
            color = appTheme().onBackground
        )
        BodyMediumText(
            text = stringResource(R.string.label_format_release_date, game.releaseDate),
            modifier = Modifier.constrainAs(releaseDateText) {
                top.linkTo(titleText.bottom, dimen8dp)
                start.linkTo(parent.start, dimen16dp)
            },
            color = appTheme().onBackground
        )
    }
}

@Composable
fun GameImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(dimen165dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimen8dp))
    )
}