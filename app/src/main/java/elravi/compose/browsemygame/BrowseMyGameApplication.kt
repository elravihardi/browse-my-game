package elravi.compose.browsemygame

import android.app.Application
import elravi.compose.browsemygame.di.apiModule
import elravi.compose.browsemygame.di.dbModule
import elravi.compose.browsemygame.di.feature.favoriteGameModule
import elravi.compose.browsemygame.di.feature.gameDetailModule
import elravi.compose.browsemygame.di.feature.gameModule
import elravi.compose.browsemygame.di.feature.gameSearchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BrowseMyGameApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BrowseMyGameApplication)
            val appModules = listOf(
                apiModule,
                gameModule,
                dbModule,
                favoriteGameModule,
                gameDetailModule,
                gameSearchModule,
            )
            modules(appModules)
        }
    }
}