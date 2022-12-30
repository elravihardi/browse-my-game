package elravi.compose.browsemygame.data.game.remote.model.response.common

import com.google.gson.annotations.SerializedName

data class GamePosterItem(
    @SerializedName("image")
    val imageUrl: String?
)