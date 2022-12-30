package elravi.compose.browsemygame.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.ui.theme.*

@Composable
fun ContentSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(dimen0dp, dimen16dp, dimen0dp, dimen0dp),
    ) {
        TitleLargeText(title)
        content()
    }
}

@Composable
fun FavoriteIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = dimen32dp,
    fillColor: Color = FavoriteIconColor,
    isFilled: Boolean = true
) {
    Icon(
        painter = painterResource(id = if (isFilled) R.drawable.ic_filled_favorite_24 else R.drawable.ic_bordered_favorite_24),
        contentDescription = stringResource(R.string.desc_fav_icon),
        modifier = modifier
            .size(iconSize)
            .clickable { onClick() },
        tint = fillColor
    )
}

@Composable
fun FavoriteIcon(
    modifier: Modifier = Modifier,
    iconSize: Dp = dimen32dp,
    fillColor: Color = FavoriteIconColor,
    isFilled: Boolean = true
) {
    Icon(
        painter = painterResource(id = if (isFilled) R.drawable.ic_filled_favorite_24 else R.drawable.ic_bordered_favorite_24),
        contentDescription = stringResource(R.string.desc_fav_icon),
        modifier = modifier.size(iconSize),
        tint = fillColor
    )
}

@Composable
fun ProfilePicture(
    @DrawableRes resourceId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageSize: Dp = dimen32dp
) {
    Image(
        modifier = modifier
            .size(imageSize)
            .clip(CircleShape)
            .clickable { onClick() },
        painter = painterResource(id = resourceId),
        contentDescription = stringResource(R.string.desc_profile_pic)
    )
}

@Composable
fun Retry(
    content: @Composable () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        content()
        Spacer(modifier = Modifier.size(dimen16dp))
        TitleSmallText(text = stringResource(R.string.action_retry), Modifier.clickable { onRetryClick() })
    }
}

@Composable
fun CircleProgressBar(
    loadingText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = appTheme().onSurface)
        Spacer(modifier = Modifier.size(dimen16dp))
        BodyMediumText(
            loadingText,
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = appTheme().onSurface
        )
    }
}

@Composable
fun VerticalSpacer(
    height: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier
        .fillMaxWidth()
        .height(height))
}

@Composable
fun HorizontalSpacer(
    width: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier
        .width(width)
        .height(dimen1dp))
}

@Composable
fun ClickableIconButton(
    @DrawableRes icon: Int,
    contentDesc: String,
    iconColorTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.clickable { onClick() }) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDesc,
            tint = iconColorTint,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ClickableCheckedIconButton(
    isCheck: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (Boolean, Modifier) -> Unit,
) {
    Box(modifier = modifier.clickable { onClick(isCheck) }) {
        icon(isCheck, Modifier.align(Alignment.Center))
    }
}