package elravi.compose.browsemygame.data.favoritegame

import elravi.compose.browsemygame.data.favoritegame.local.FavoriteGameDao
import elravi.compose.browsemygame.data.favoritegame.local.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteGameRepositoryImpl(
    private val favoriteGameDao: FavoriteGameDao,
) : FavoriteGameRepository {

    override fun getAllFavoriteGame(): Flow<List<FavoriteGameEntity>> {
        return flow {
            emit(favoriteGameDao.getAllFavGame())
        }
    }

    override suspend fun getGameFavoriteStatus(gameId: Int): Boolean {
        return try {
            favoriteGameDao.getFavoriteStatusFromId(gameId)
        } catch (ex: Exception) {
            false
        }
    }

    override suspend fun addFavoriteGame(game: FavoriteGameEntity): Boolean {
        return try {
            favoriteGameDao.insert(game)
            true
        } catch (ex: Exception) {
            false
        }
    }

    override suspend fun removeFavoriteGame(gameId: Int): Boolean {
        return try {
            favoriteGameDao.delete(gameId)
            true
        } catch (ex: Exception) {
            false
        }
    }
}