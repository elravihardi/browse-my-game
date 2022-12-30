package elravi.compose.browsemygame.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elravi.compose.browsemygame.domain.game.GameUseCase
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.ui.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val gameUseCase: GameUseCase,
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO,
): ViewModel() {

    private var lastPage = 0

    var allGames = listOf<Game>()
        private set

    private val _newReleaseGameList = MutableStateFlow<UiState<List<Game>>>(UiState.default())
    val newReleaseGameList: StateFlow<UiState<List<Game>>> = _newReleaseGameList

    fun getNewReleaseGame() {
        _newReleaseGameList.value = UiState.loading()
        lastPage++
        viewModelScope.launch(ioDispatchers) {
            gameUseCase.getNewReleaseGame(lastPage)
                .catch { ex ->
                    --lastPage
                    _newReleaseGameList.value = UiState.fail(ex, ex.message)
                }
                .collect {
                    _newReleaseGameList.value =
                        if (it.results.isNotEmpty()) {
                            allGames = allGames + it.results
                            UiState.success(allGames)
                        }
                        else {
                            --lastPage
                            UiState.empty()
                        }
                }
        }
    }
}