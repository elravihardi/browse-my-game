package elravi.compose.browsemygame.ui.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.appTheme
import elravi.compose.browsemygame.ui.components.*
import elravi.compose.browsemygame.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

private const val dicodingUrl = "https://www.dicoding.com/users/elravihardi/academies"
private const val elraviEmail = "elravi30@gmail.com"

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier
            .fillMaxSize()
            .background(color = appTheme().background)
            .padding(horizontal = dimen16dp, vertical = dimen0dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(height = dimen24dp)
        Icon(
            painter = painterResource(id = R.drawable.ic_back_24),
            contentDescription = stringResource(R.string.desc_back_button),
            tint = appTheme().onSurface,
            modifier = Modifier
                .clickable { navController.navigateUp() }
                .align(Alignment.Start)
        )

        VerticalSpacer(height = dimen32dp)
        ProfilePicture(
            R.drawable.img_profile_picture,
            onClick = { uriHandler.openUri(dicodingUrl) },
            imageSize = dimen100dp
        )

        VerticalSpacer(height = dimen32dp)
        HeadlineMediumText(
            text = "Elravi Hardi",
            color = appTheme().primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { uriHandler.openUri(dicodingUrl) }
        )

        VerticalSpacer(height = dimen16dp)
        SelectionContainer {
            TitleMediumText(
                text = elraviEmail,
                color = appTheme().onSurface
            )
        }

        VerticalSpacer(height = dimen48dp)
        BodyLargeText(
            text = stringResource(R.string.text_app_tech_stack),
            modifier = Modifier.align(Alignment.Start),
            color = appTheme().onSurface
        )

        VerticalSpacer(height = dimen16dp)
        val techStackList = listOf(
            "Jetpack Compose", "Navigation Compose", "Retrofit", "Koin", "Room"
        )
        techStackList.forEach {
            BodyMediumText(
                text = "•  $it",
                modifier = Modifier.align(Alignment.Start),
                color = appTheme().onSurface,
            )
            VerticalSpacer(height = dimen8dp)
        }
        Spacer(modifier = Modifier.weight(1.0f))

        VerticalSpacer(height = dimen24dp)
        val day = SimpleDateFormat("EEEE", Locale.US).format(Date())
        BodySmallText(
            text = stringResource(id = R.string.text_greeting_day, day),
            color = appTheme().onSurface,
        )
        VerticalSpacer(height = dimen24dp)
    }
}