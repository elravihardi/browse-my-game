package elravi.compose.browsemygame.domain.gamesearch

import elravi.compose.browsemygame.domain.game.model.response.gamelist.GameList
import kotlinx.coroutines.flow.Flow

interface GameSearchUseCase {
    fun searchGame(searchQuery: String, page: Int): Flow<GameList>
}