package elravi.compose.browsemygame.domain.gamedetail

import elravi.compose.browsemygame.domain.gamedetail.model.response.GameDetail
import elravi.compose.browsemygame.domain.gamedetail.model.response.GamePoster
import kotlinx.coroutines.flow.Flow

interface GameDetailUseCase {
    fun getGameDetail(id: Int): Flow<GameDetail>
    fun getGamePoster(id: Int): Flow<List<GamePoster>>
}