package elravi.compose.browsemygame.domain.gamedetail.mapper

import elravi.compose.browsemygame.data.favoritegame.local.FavoriteGameEntity
import elravi.compose.browsemygame.data.game.remote.model.response.common.GamePosterItem
import elravi.compose.browsemygame.data.game.remote.model.response.common.GenreItem
import elravi.compose.browsemygame.data.game.remote.model.response.common.StoreItem
import elravi.compose.browsemygame.data.game.remote.model.response.detail.*
import elravi.compose.browsemygame.domain.game.mapper.getDrawableFromId
import elravi.compose.browsemygame.domain.game.model.response.common.Genre
import elravi.compose.browsemygame.domain.game.model.response.common.Store
import elravi.compose.browsemygame.domain.gamedetail.model.response.*
import elravi.compose.browsemygame.util.getMetacriticScoreColor
import elravi.compose.browsemygame.util.orDash
import elravi.compose.browsemygame.util.orZero

fun GameDetail.toFavoriteGameEntity() = FavoriteGameEntity(
    id,
    title,
    metacritic,
    metacriticColor.value.toLong(),
    parentPlatforms.joinToString(","),
    slug,
    released,
    gamePosterUrl,
    true
)

fun GameDetailResponse.toGameDetail(isFavorite: Boolean = false): GameDetail {
    return GameDetail(
        id = id.orZero(),
        slug = slug.orDash(),
        title = title.orDash(),
        released = released.orDash(),
        updated = updated.orDash(),
        added = added.orDash(),
        developers = developers?.mapNotNull { it.name }?.joinToString(", ").orEmpty(),
        publishers = publishers?.mapNotNull { it.name }?.joinToString(", ").orEmpty(),
        metacritic = metacritic.orDash(),
        metacriticColor = metacritic.getMetacriticScoreColor(),
        rating = "${rating.orDash()} / ${ratingTop.orDash()}",
        playtime = playtime.orDash(),
        parentPlatforms = parentPlatforms?.let { list ->
            list.mapNotNull { platform ->
                platform.platformItem?.getDrawableFromId()
            }
        }.orEmpty(),
        platforms = platforms?.mapNotNull { it.platformItem }
            ?.mapNotNull { it.name }
            ?.joinToString(", ")
            .orEmpty(),
        description = description ?: "Not Available",
        backgroundImage,
        genres = genres?.mapNotNull { it.name }?.joinToString(", ").orEmpty(),
        addedByStatus = addedByStatus?.toAddedByStatus(),
        website = website ?: "Not Available",
        suggestionsCount = suggestionsCount.orDash(),
        stores = stores?.mapNotNull { it.store }
            ?.mapNotNull { it.name }
            ?.joinToString(", ")
            .orEmpty(),
        esrbRating = esrbRating?.toEsrbRating(),
        isFavorite = isFavorite
    )
}

fun GamePosterItem.toGamePoster() = GamePoster(
    imageUrl.orEmpty()
)

fun GenreItem.toGenre() = Genre(
    name.orDash(),
    id.orZero(),
    slug.orDash()
)

fun StoreItem.toStore() = Store(
    name.orDash(),
    id.orZero(),
    slug.orDash(),
    domain.orDash()
)

fun AddedByStatusItem.toAddedByStatus() = AddedByStatus(
    owned.orZero(),
    beaten.orZero(),
    dropped = dropped.orZero(),
    yet = yet.orZero(),
    playing = playing.orZero(),
    toplay = toplay.orZero()
)

fun DeveloperItem.toDeveloper() = Developer(
    name.orDash(),
    id.orZero(),
    slug.orDash()
)

fun EsrbRatingItem.toEsrbRating() = EsrbRating(
    name.orDash(),
    id.orZero(),
    slug.orDash()
)

fun PublisherItem.toPublisher() = Publisher(
    name.orDash(),
    id.orZero(),
    slug.orDash()
)

fun RatingItem.toRating() = Rating(
    count.orZero(),
    id.orZero(),
    title.orDash(),
    percent.orZero()
)