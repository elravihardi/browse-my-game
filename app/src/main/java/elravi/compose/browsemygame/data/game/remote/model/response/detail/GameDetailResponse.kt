package elravi.compose.browsemygame.data.game.remote.model.response.detail

import com.google.gson.annotations.SerializedName
import elravi.compose.browsemygame.data.game.remote.model.response.common.GenreItem
import elravi.compose.browsemygame.data.game.remote.model.response.common.StoreItem
import elravi.compose.browsemygame.data.game.remote.model.response.gamelist.PlatformsItem

data class GameDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("name")
    val title: String?,
    @SerializedName("released")
    val released: String?,
    @SerializedName("updated")
    val updated: String?,
    @SerializedName("added")
    val added: Int?,
    @SerializedName("developers")
    val developers: List<DeveloperItem>?,
    @SerializedName("publishers")
    val publishers: List<PublisherItem>?,
    @SerializedName("metacritic")
    val metacritic: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("rating_top")
    val ratingTop: Int?,
    @SerializedName("ratings")
    val ratings: List<RatingItem>?,
    @SerializedName("playtime")
    val playtime: Int?,
    @SerializedName("parent_platforms")
    val parentPlatforms: List<PlatformsItem>?,
    @SerializedName("platforms")
    val platforms: List<PlatformsItem>?,
    @SerializedName("description_raw")
    val description: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("reviews_count")
    val reviewsCount: Int?,
    @SerializedName("genres")
    val genres: List<GenreItem>?,
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatusItem?,
    @SerializedName("website")
    val website: String?,
    @SerializedName("suggestions_count")
    val suggestionsCount: Int?,
    @SerializedName("stores")
    val stores: List<StoresItem>?,
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRatingItem?
)

data class StoresItem(
    @SerializedName("store") val store: StoreItem?
)