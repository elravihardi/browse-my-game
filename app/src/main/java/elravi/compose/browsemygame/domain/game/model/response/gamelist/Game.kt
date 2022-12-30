package elravi.compose.browsemygame.domain.game.model.response.gamelist

import androidx.compose.ui.graphics.Color

data class Game(
    val id: Int,
    val title: String,
    val metacritic: String,
    val metacriticColor: Color,
    val platforms: List<Int>,
    val slug: String,
    val releaseDate: String,
    val gamePosterUrl: String?,
    val isFavorite: Boolean = false
)