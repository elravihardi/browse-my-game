package elravi.compose.browsemygame.data.game.remote

import elravi.compose.browsemygame.data.game.remote.model.response.detail.GameDetailResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gamelist.GameListResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gameposterlist.GamePosterListResponse
import retrofit2.Response

class GameApi(private val gameApiClient: GameApiClient): GameApiClient {
    override suspend fun getGameList(gamesListRequest: Map<String, String>): Response<GameListResponse> =
        gameApiClient.getGameList(gamesListRequest)

    override suspend fun getGameDetail(id: String): Response<GameDetailResponse> =
        gameApiClient.getGameDetail(id)

    override suspend fun getGamePoster(id: String): Response<GamePosterListResponse> =
        gameApiClient.getGamePoster(id)
}