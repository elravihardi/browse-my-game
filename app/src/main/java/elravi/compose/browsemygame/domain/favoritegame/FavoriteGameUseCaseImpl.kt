package elravi.compose.browsemygame.domain.favoritegame

import elravi.compose.browsemygame.data.favoritegame.FavoriteGameRepository
import elravi.compose.browsemygame.domain.game.mapper.toFavoriteGameEntity
import elravi.compose.browsemygame.domain.game.mapper.toGame
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.domain.gamedetail.mapper.toFavoriteGameEntity
import elravi.compose.browsemygame.domain.gamedetail.model.response.GameDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteGameUseCaseImpl(private val favoriteGameRepository: FavoriteGameRepository): FavoriteGameUseCase {
    override fun getAllFavoriteGame(): Flow<List<Game>> {
        return favoriteGameRepository.getAllFavoriteGame().map {
            it.map { item -> item.toGame() }
        }
    }

    override suspend fun getGameFavoriteStatus(gameId: Int): Boolean {
        return favoriteGameRepository.getGameFavoriteStatus(gameId)
    }

    override suspend fun addFavoriteGame(game: Game): Boolean {
        return favoriteGameRepository.addFavoriteGame(game.toFavoriteGameEntity())
    }

    override suspend fun addFavoriteGame(gameDetail: GameDetail): Boolean {
        return favoriteGameRepository.addFavoriteGame(gameDetail.toFavoriteGameEntity())
    }

    override suspend fun removeFavoriteGame(gameId: Int): Boolean {
        return favoriteGameRepository.removeFavoriteGame(gameId)
    }

}