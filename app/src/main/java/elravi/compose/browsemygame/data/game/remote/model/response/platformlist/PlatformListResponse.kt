package elravi.compose.browsemygame.data.game.remote.model.response.platformlist

import com.google.gson.annotations.SerializedName
import elravi.compose.browsemygame.data.game.remote.model.response.common.PlatformItem

data class PlatformListResponse(
    @SerializedName("results")
    val results: List<PlatformItem>?
)