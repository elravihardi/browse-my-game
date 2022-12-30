package elravi.compose.browsemygame.data.game.remote.model.response.gamelist

import com.google.gson.annotations.SerializedName

data class GameListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<GameItem>?
)