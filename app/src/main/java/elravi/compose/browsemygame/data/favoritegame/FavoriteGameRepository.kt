package elravi.compose.browsemygame.data.favoritegame

import elravi.compose.browsemygame.data.favoritegame.local.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteGameRepository {
    fun getAllFavoriteGame(): Flow<List<FavoriteGameEntity>>
    suspend fun getGameFavoriteStatus(gameId: Int): Boolean
    suspend fun addFavoriteGame(game: FavoriteGameEntity): Boolean
    suspend fun removeFavoriteGame(gameId: Int): Boolean
}