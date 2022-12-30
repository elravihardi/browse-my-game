package elravi.compose.browsemygame.di

import elravi.compose.browsemygame.infra.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {

    single { AppDatabase.getDatabase(androidApplication().applicationContext) }

    single {
        val appDatabase: AppDatabase = get()
        return@single appDatabase.favoriteGameDao()
    }
}