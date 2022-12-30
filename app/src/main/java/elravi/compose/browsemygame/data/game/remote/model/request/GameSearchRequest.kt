package elravi.compose.browsemygame.data.game.remote.model.request

import com.google.gson.annotations.SerializedName

data class GameSearchRequest(
    @SerializedName("search") val searchQuery: String,
    @SerializedName("search_precise") val isPreciseSearch: Boolean = true,
    @SerializedName("page") val page: String,
    @SerializedName("page_size") val pageSize: String,
    @SerializedName("ordering") val ordering: String = "-added",
)