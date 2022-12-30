package elravi.compose.browsemygame.data.game.remote.model.request

import com.google.gson.annotations.SerializedName

data class GameDateRangeRequest(
    @SerializedName("dates") val dates: String,
    @SerializedName("ordering") val orderBy: String,
    @SerializedName("page") val page: String,
    @SerializedName("page_size") val pageSize: String
)