package elravi.compose.browsemygame.data.favoritegame.local

import androidx.room.*

@Dao
interface FavoriteGameDao {

    @Query("SELECT * from $FAV_GAME_TABLE")
    suspend fun getAllFavGame(): List<FavoriteGameEntity>

    @Query("SELECT isFavorite FROM $FAV_GAME_TABLE WHERE id = :gameId")
    suspend fun getFavoriteStatusFromId(gameId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favGame: FavoriteGameEntity)

    @Query("DELETE FROM $FAV_GAME_TABLE WHERE id = :gameId")
    suspend fun delete(gameId: Int)
}