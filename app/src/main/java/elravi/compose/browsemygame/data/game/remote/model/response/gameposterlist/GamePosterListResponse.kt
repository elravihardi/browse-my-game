package elravi.compose.browsemygame.data.game.remote.model.response.gameposterlist

import com.google.gson.annotations.SerializedName
import elravi.compose.browsemygame.data.game.remote.model.response.common.GamePosterItem

data class GamePosterListResponse(
    @SerializedName("results")
    val results: List<GamePosterItem>?
)