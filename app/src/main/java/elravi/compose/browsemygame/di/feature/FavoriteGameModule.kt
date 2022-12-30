package elravi.compose.browsemygame.di.feature

import elravi.compose.browsemygame.data.favoritegame.FavoriteGameRepository
import elravi.compose.browsemygame.data.favoritegame.FavoriteGameRepositoryImpl
import elravi.compose.browsemygame.domain.favoritegame.FavoriteGameUseCase
import elravi.compose.browsemygame.domain.favoritegame.FavoriteGameUseCaseImpl
import elravi.compose.browsemygame.infra.db.AppDatabase
import elravi.compose.browsemygame.ui.feature.favorite.FavoriteGameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteGameModule = module {

    single {
        val appDatabase: AppDatabase = get()
        return@single appDatabase.favoriteGameDao()
    }

    single<FavoriteGameRepository> { FavoriteGameRepositoryImpl(get()) }

    single<FavoriteGameUseCase> { FavoriteGameUseCaseImpl(get()) }

    viewModel { FavoriteGameViewModel(get()) }
}