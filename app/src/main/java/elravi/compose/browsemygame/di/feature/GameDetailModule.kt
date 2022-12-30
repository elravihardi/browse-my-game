package elravi.compose.browsemygame.di.feature

import elravi.compose.browsemygame.domain.gamedetail.GameDetailUseCase
import elravi.compose.browsemygame.domain.gamedetail.GameDetailUseCaseImpl
import elravi.compose.browsemygame.ui.feature.gamedetail.GameDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gameDetailModule = module {
    single<GameDetailUseCase> { GameDetailUseCaseImpl(get(), get()) }

    viewModel { GameDetailViewModel(get(), get()) }
}