package elravi.compose.browsemygame.data.game.remote.model.response.detail

import com.google.gson.annotations.SerializedName

data class AddedByStatusItem(
    @SerializedName("owned")
    val owned: Int?,
    @SerializedName("beaten")
    val beaten: Int?,
    @SerializedName("dropped")
    val dropped: Int?,
    @SerializedName("yet")
    val yet: Int?,
    @SerializedName("playing")
    val playing: Int?,
    @SerializedName("toplay")
    val toplay: Int?
)