package elravi.compose.browsemygame.data.favoritegame.local

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAV_GAME_TABLE = "favorite_game_table"

@Entity(tableName = FAV_GAME_TABLE)
data class FavoriteGameEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val metacritic: String,
    val metacriticColor: Long,
    val platforms: String,
    val slug: String,
    val releaseDate: String,
    val bgImageUrl: String?,
    val isFavorite: Boolean = true
)