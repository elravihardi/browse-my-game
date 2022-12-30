package elravi.compose.browsemygame.infra.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import elravi.compose.browsemygame.data.favoritegame.local.FavoriteGameDao
import elravi.compose.browsemygame.data.favoritegame.local.FavoriteGameEntity

@Database(
    entities = [FavoriteGameEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(appContext: Context, dbName: String = "browse-my-game-db"): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        appContext,
                        AppDatabase::class.java,
                        dbName
                    )
                    .addCallback(FavoriteDatabaseCallback())
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private class FavoriteDatabaseCallback : Callback()
    }

    abstract fun favoriteGameDao(): FavoriteGameDao

}
