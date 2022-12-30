package elravi.compose.browsemygame.data.game

import elravi.compose.browsemygame.data.game.remote.GameApi
import elravi.compose.browsemygame.data.game.remote.model.request.GameDateRangeRequest
import elravi.compose.browsemygame.data.game.remote.model.request.GameSearchRequest
import elravi.compose.browsemygame.data.game.remote.model.response.detail.GameDetailResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gamelist.GameListResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gameposterlist.GamePosterListResponse
import elravi.compose.browsemygame.util.handleApiError
import elravi.compose.browsemygame.util.toMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameRepositoryImpl(
    private val gameApi: GameApi,
) : GameRepository {

    override fun getNewReleaseGame(gameDateRangeRequest: GameDateRangeRequest): Flow<GameListResponse> =
        flow {
            emit(
                gameApi.getGameList(gameDateRangeRequest.toMap() ?: mapOf())
                    .handleApiError()
            )
        }

    override fun getGameDetail(id: String): Flow<GameDetailResponse> {
        return flow {
            emit(
                gameApi.getGameDetail(id)
                    .handleApiError()
            )
        }
    }

    override fun getGamePoster(id: String): Flow<GamePosterListResponse> {
        return flow {
            emit(
                gameApi.getGamePoster(id)
                    .handleApiError()
            )
        }
    }

    override fun searchGame(gameSearchRequest: GameSearchRequest): Flow<GameListResponse> {
        return flow {
            emit(
                gameApi.getGameList(gameSearchRequest.toMap() ?: mapOf())
                    .handleApiError()
            )
        }
    }
}