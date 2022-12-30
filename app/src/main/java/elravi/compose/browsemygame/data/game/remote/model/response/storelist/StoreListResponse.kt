package elravi.compose.browsemygame.data.game.remote.model.response.storelist

import com.google.gson.annotations.SerializedName
import elravi.compose.browsemygame.data.game.remote.model.response.common.StoreItem

data class StoreListResponse(
    @SerializedName("results")
    val results: List<StoreItem>?
)