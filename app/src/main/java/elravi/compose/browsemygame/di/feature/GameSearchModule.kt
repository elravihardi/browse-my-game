package elravi.compose.browsemygame.di.feature

import elravi.compose.browsemygame.domain.gamesearch.GameSearchUseCase
import elravi.compose.browsemygame.domain.gamesearch.GameSearchUseCaseImpl
import elravi.compose.browsemygame.ui.feature.gamesearch.GameSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gameSearchModule = module {
    single<GameSearchUseCase> { GameSearchUseCaseImpl(get()) }

    viewModel { GameSearchViewModel(get()) }
}