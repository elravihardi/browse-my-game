package elravi.compose.browsemygame.data.game.remote.model.response.detail

import com.google.gson.annotations.SerializedName

data class DeveloperItem(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?
)