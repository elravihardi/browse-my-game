package elravi.compose.browsemygame.data.game.remote

import elravi.compose.browsemygame.data.game.remote.model.response.detail.GameDetailResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gamelist.GameListResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gameposterlist.GamePosterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GameApiClient {
    @GET("/api/games?")
    suspend fun getGameList(@QueryMap gamesListRequest: Map<String, String>): Response<GameListResponse>

    @GET("/api/games/{id}")
    suspend fun getGameDetail(@Path("id") id: String): Response<GameDetailResponse>

    @GET("/api/games/{id}/screenshots")
    suspend fun getGamePoster(@Path("id") id: String): Response<GamePosterListResponse>
}