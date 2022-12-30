package elravi.compose.browsemygame.domain.gamesearch

import elravi.compose.browsemygame.data.game.GameRepository
import elravi.compose.browsemygame.data.game.remote.model.request.GameSearchRequest
import elravi.compose.browsemygame.domain.game.mapper.toGameList
import elravi.compose.browsemygame.domain.game.model.response.gamelist.GameList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameSearchUseCaseImpl(
    private val gameRepository: GameRepository
): GameSearchUseCase {

    private val pageSize = 20

    override fun searchGame(searchQuery: String, page: Int): Flow<GameList> {
        return gameRepository.searchGame(
                GameSearchRequest(
                    searchQuery = searchQuery,
                    isPreciseSearch = true,
                    page = page.toString(),
                    pageSize = pageSize.toString(),
                    ordering = "-added"
                )
            )
            .map { it.toGameList() }
    }
}