package elravi.compose.browsemygame.data.game.remote.model.response.gamelist

import com.google.gson.annotations.SerializedName
import elravi.compose.browsemygame.data.game.remote.model.response.common.PlatformItem

data class GameItem(
    @SerializedName("metacritic")
    val metacritic: Int?,
    @SerializedName("parent_platforms")
    val platforms: List<PlatformsItem>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("released")
    val releaseDate: String?,
    @SerializedName("background_image")
    val gamePosterUrl: String?,
    @SerializedName("name")
    val name: String?
)

data class PlatformsItem(
    @SerializedName("platform") val platformItem: PlatformItem?
)