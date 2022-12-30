package elravi.compose.browsemygame.data.game.remote.model.response.common

import com.google.gson.annotations.SerializedName

data class PlatformItem(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?
)