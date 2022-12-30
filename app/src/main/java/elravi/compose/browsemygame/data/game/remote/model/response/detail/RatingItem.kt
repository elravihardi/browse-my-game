package elravi.compose.browsemygame.data.game.remote.model.response.detail

import com.google.gson.annotations.SerializedName

data class RatingItem(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("percent")
    val percent: Double?
)