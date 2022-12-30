package elravi.compose.browsemygame.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun BodySmallText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    Text(modifier = modifier, text = text, lineHeight = lineHeight, textAlign = textAlign, color = color, style = MaterialTheme.typography.bodySmall)
}

@Composable
fun BodyMediumText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    Text(modifier = modifier, text = text, lineHeight = lineHeight, textAlign = textAlign, color = color, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun BodyLargeText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(modifier = modifier, text = text, textAlign = textAlign, color = color, style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun TitleLargeText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(modifier = modifier, text = text, textAlign = textAlign, color = color, style = MaterialTheme.typography.titleLarge)
}

@Composable
fun TitleMediumText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(modifier = modifier, text = text, textAlign = textAlign, color = color, style = MaterialTheme.typography.titleMedium)
}

@Composable
fun TitleSmallText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(modifier = modifier, text = text, textAlign = textAlign, color = color, style = MaterialTheme.typography.titleSmall)
}

@Composable
fun HeadlineMediumText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(modifier = modifier, text = text, textAlign = textAlign, color = color, style = MaterialTheme.typography.headlineMedium)
}