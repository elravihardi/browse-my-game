package elravi.compose.browsemygame.data.game.remote.model.response.common

import com.google.gson.annotations.SerializedName

data class StoreItem(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("domain")
    val domain: String?
)