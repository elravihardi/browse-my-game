package elravi.compose.browsemygame.util

import elravi.compose.browsemygame.ui.theme.BlueScoreColor
import elravi.compose.browsemygame.ui.theme.RedScoreColor
import elravi.compose.browsemygame.ui.theme.YellowScoreColor

fun Int?.orZero(): Int = this ?: 0
fun Double?.orZero(): Double = this ?: 0.0
fun Float?.orZero(): Float = this ?: 0f
fun Long?.orZero(): Long = this ?: 0

fun Int?.getMetacriticScoreColor() = this.orZero().let {
    when {
        it >= 70 -> BlueScoreColor
        it >= 50 -> YellowScoreColor
        else -> RedScoreColor
    }
}