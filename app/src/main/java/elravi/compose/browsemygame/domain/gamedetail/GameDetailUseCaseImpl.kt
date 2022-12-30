package elravi.compose.browsemygame.domain.gamedetail

import elravi.compose.browsemygame.data.favoritegame.FavoriteGameRepository
import elravi.compose.browsemygame.data.game.GameRepository
import elravi.compose.browsemygame.domain.gamedetail.mapper.toGameDetail
import elravi.compose.browsemygame.domain.gamedetail.mapper.toGamePoster
import elravi.compose.browsemygame.domain.gamedetail.model.response.GameDetail
import elravi.compose.browsemygame.domain.gamedetail.model.response.GamePoster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameDetailUseCaseImpl(
    private val gameRepository: GameRepository,
    private val favoriteGameRepository: FavoriteGameRepository,
) : GameDetailUseCase {

    override fun getGameDetail(id: Int): Flow<GameDetail> {
        return gameRepository.getGameDetail(id.toString()).map {
            val isFavorite = favoriteGameRepository.getGameFavoriteStatus(id)
            it.toGameDetail(isFavorite)
        }
    }

    override fun getGamePoster(id: Int): Flow<List<GamePoster>> {
        return gameRepository.getGamePoster(id.toString()).map {
            it.results.orEmpty().map { item -> item.toGamePoster() }
        }
    }
}