package elravi.compose.browsemygame.domain.game.mapper

import androidx.compose.ui.graphics.Color
import elravi.compose.browsemygame.R
import elravi.compose.browsemygame.data.favoritegame.local.FavoriteGameEntity
import elravi.compose.browsemygame.data.game.remote.model.response.common.PlatformItem
import elravi.compose.browsemygame.data.game.remote.model.response.gamelist.GameItem
import elravi.compose.browsemygame.data.game.remote.model.response.gamelist.GameListResponse
import elravi.compose.browsemygame.domain.game.model.response.common.Platform
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.domain.game.model.response.gamelist.GameList
import elravi.compose.browsemygame.util.getMetacriticScoreColor
import elravi.compose.browsemygame.util.orDash
import elravi.compose.browsemygame.util.orZero

fun GameListResponse.toGameList() = GameList(
    count = count,
    next = next,
    previous = previous,
    results = results?.let { list ->
        list.map { it.toGame(false) }
    }.orEmpty()
)

fun GameItem.toGame(isFavorite: Boolean): Game {
    return Game(
        id = id.orZero(),
        title = name.orDash(),
        metacritic = metacritic.orDash(),
        slug = slug.orDash(),
        releaseDate = releaseDate.orDash(),
        gamePosterUrl = gamePosterUrl,
        metacriticColor = metacritic.getMetacriticScoreColor(),
        platforms = platforms?.let { list ->
            list.mapNotNull { platform ->
                platform.platformItem?.getDrawableFromId()
            }
        }.orEmpty(),
        isFavorite = isFavorite
    )
}

fun PlatformItem?.getDrawableFromId(): Int? {
    return this?.id?.let {
        when (it) {
            1 -> R.drawable.ic_windows
            2 -> R.drawable.ic_playstation
            3 -> R.drawable.ic_xbox
            4 -> R.drawable.ic_ios
            5 -> R.drawable.ic_macos
            6 -> R.drawable.ic_linux
            7 -> R.drawable.ic_nintendo
            8 -> R.drawable.ic_android
            14 -> R.drawable.ic_web
            else -> null
        }
    }
}

fun PlatformItem.toPlatform() = Platform(
    name.orDash(),
    id.orZero(),
    slug.orDash()
)

fun Game.toFavoriteGameEntity(): FavoriteGameEntity {
    return FavoriteGameEntity(
        id,
        title,
        metacritic,
        metacriticColor.value.toLong(),
        platforms.joinToString(","),
        slug,
        releaseDate,
        gamePosterUrl,
        true
    )
}

fun FavoriteGameEntity.toGame(): Game {
    return Game(
        id,
        title,
        metacritic,
        Color(metacriticColor.toULong()),
        platforms.split(',').map { item -> item.toIntOrNull().orZero() },
        slug,
        releaseDate,
        bgImageUrl,
        isFavorite
    )
}