package elravi.compose.browsemygame.ui.feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elravi.compose.browsemygame.domain.favoritegame.FavoriteGameUseCase
import elravi.compose.browsemygame.domain.game.model.response.gamelist.Game
import elravi.compose.browsemygame.infra.ui.Event
import elravi.compose.browsemygame.ui.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteGameViewModel(
    private val favoriteGameUseCase: FavoriteGameUseCase,
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _allFavoriteGameState = MutableStateFlow<UiState<List<Game>>>(UiState.default())
    val allFavoriteGameState: StateFlow<UiState<List<Game>>> = _allFavoriteGameState

    fun getAllFavoriteGame() {
        _allFavoriteGameState.value = UiState.loading()
        viewModelScope.launch(ioDispatchers) {
            favoriteGameUseCase.getAllFavoriteGame()
                .catch { ex ->
                    _allFavoriteGameState.value = UiState.fail(ex, ex.message)
                }
                .collect {
                    _allFavoriteGameState.value =
                        if (it.isNotEmpty()) {
                            UiState.success(it)
                        }
                        else {
                            UiState.empty()
                        }
                }
        }
    }

    private val _removeFavGameState = MutableStateFlow<Event<UiState<Game>>>(Event(UiState.default()))
    val removeFavGameState: StateFlow<Event<UiState<Game>>> = _removeFavGameState

    fun removeFavGame(game: Game) {
        viewModelScope.launch(ioDispatchers) {
            val isRemoveGameSuccess = favoriteGameUseCase.removeFavoriteGame(game.id)
            if (isRemoveGameSuccess) {
                _removeFavGameState.value = Event(UiState.success(game))
            } else {
                _removeFavGameState.value = Event(UiState.fail(null))
            }
        }
    }
}