package elravi.compose.browsemygame.domain.gamedetail.model.response

import androidx.compose.ui.graphics.Color

data class GameDetail(
    val id: Int,
    val slug: String,
    val title: String,
    val released: String,
    val updated: String,
    val added: String,
    val developers: String,
    val publishers: String,
    val metacritic: String,
    val metacriticColor: Color,
    val rating: String,
    val playtime: String,
    val parentPlatforms: List<Int>,
    val platforms: String,
    val description: String,
    val gamePosterUrl: String?,
    val genres: String,
    val addedByStatus: AddedByStatus?,
    val website: String,
    val suggestionsCount: String,
    val stores: String,
    val esrbRating: EsrbRating?,
    val isFavorite: Boolean,
)