package elravi.compose.browsemygame.domain.favoritegame

import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.domain.gamedetail.model.response.GameDetail
import kotlinx.coroutines.flow.Flow

interface FavoriteGameUseCase {
    fun getAllFavoriteGame(): Flow<List<Game>>
    suspend fun getGameFavoriteStatus(gameId: Int): Boolean
    suspend fun addFavoriteGame(game: Game): Boolean
    suspend fun addFavoriteGame(gameDetail: GameDetail): Boolean
    suspend fun removeFavoriteGame(gameId: Int): Boolean
}