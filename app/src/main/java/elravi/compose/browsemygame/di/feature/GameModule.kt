package elravi.compose.browsemygame.di.feature

import elravi.compose.browsemygame.data.game.GameRepository
import elravi.compose.browsemygame.data.game.GameRepositoryImpl
import elravi.compose.browsemygame.data.game.remote.GameApi
import elravi.compose.browsemygame.data.game.remote.GameApiClient
import elravi.compose.browsemygame.di.RAWG_BASE_URL
import elravi.compose.browsemygame.domain.game.GameUseCase
import elravi.compose.browsemygame.domain.game.GameUseCaseImpl
import elravi.compose.browsemygame.infra.network.ApiService
import elravi.compose.browsemygame.ui.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val gameModule = module {

    single {
        ApiService.createService(
            GameApiClient::class.java,
            get(),
            get(named(RAWG_BASE_URL))
        )
    }

    single { GameApi(get()) }

    single<GameRepository> { GameRepositoryImpl(get()) }

    single<GameUseCase> { GameUseCaseImpl(get()) }

    viewModel { HomeViewModel(get()) }
}