package elravi.compose.browsemygame.data.game.remote.model.response.detail

import com.google.gson.annotations.SerializedName

data class PublisherItem(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?
)