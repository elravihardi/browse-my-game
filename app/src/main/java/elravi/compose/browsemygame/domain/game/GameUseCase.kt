package elravi.compose.browsemygame.domain.game

import elravi.compose.browsemygame.domain.game.model.response.gamelist.GameList
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getNewReleaseGame(page: Int): Flow<GameList>
}