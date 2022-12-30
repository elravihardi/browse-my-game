package elravi.compose.browsemygame.data.game.remote.model.response.genrelist

import com.google.gson.annotations.SerializedName
import elravi.compose.browsemygame.data.game.remote.model.response.common.GenreItem

data class GenreListResponse(
    @SerializedName("results")
    val results: List<GenreItem>?
)