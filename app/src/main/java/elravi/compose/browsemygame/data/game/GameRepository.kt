package elravi.compose.browsemygame.data.game

import elravi.compose.browsemygame.data.game.remote.model.request.GameDateRangeRequest
import elravi.compose.browsemygame.data.game.remote.model.request.GameSearchRequest
import elravi.compose.browsemygame.data.game.remote.model.response.detail.GameDetailResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gamelist.GameListResponse
import elravi.compose.browsemygame.data.game.remote.model.response.gameposterlist.GamePosterListResponse
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getNewReleaseGame(gameDateRangeRequest: GameDateRangeRequest): Flow<GameListResponse>
    fun getGameDetail(id: String): Flow<GameDetailResponse>
    fun getGamePoster(id: String): Flow<GamePosterListResponse>
    fun searchGame(gameSearchRequest: GameSearchRequest): Flow<GameListResponse>
}