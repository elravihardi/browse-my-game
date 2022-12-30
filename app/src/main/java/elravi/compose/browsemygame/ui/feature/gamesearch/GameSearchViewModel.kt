package elravi.compose.browsemygame.ui.feature.gamesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.domain.gamesearch.GameSearchUseCase
import elravi.compose.browsemygame.ui.UiState
import elravi.compose.browsemygame.util.DataNotFoundException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GameSearchViewModel(
    private val gameSearchUseCase: GameSearchUseCase,
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO,
): ViewModel() {

    private var lastPage = 0

    var allGames = listOf<Game>()
        private set

    private val _gameSearchResult = MutableStateFlow<UiState<List<Game>>>(UiState.default())
    val gameSearchResult: StateFlow<UiState<List<Game>>> = _gameSearchResult

    fun startNewSearch(searchQuery: String) {
        lastPage = 0
        allGames = listOf()
        searchGame(searchQuery)
    }

    fun searchGame(searchQuery: String) {
        _gameSearchResult.value = UiState.loading()
        lastPage++

        viewModelScope.launch(ioDispatchers) {
            gameSearchUseCase.searchGame(searchQuery, lastPage)
                .catch { ex ->
                    --lastPage
                    when (ex) {
                        is DataNotFoundException -> {
                            _gameSearchResult.value = UiState.empty()
                        }
                        else -> {
                            _gameSearchResult.value = UiState.fail(ex, ex.message)
                        }
                    }
                }
                .collect {
                    _gameSearchResult.value =
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