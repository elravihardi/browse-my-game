package elravi.compose.browsemygame.ui.feature.gamedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elravi.compose.browsemygame.domain.favoritegame.FavoriteGameUseCase
import elravi.compose.browsemygame.domain.gamedetail.GameDetailUseCase
import elravi.compose.browsemygame.domain.gamedetail.model.response.GameDetail
import elravi.compose.browsemygame.domain.gamedetail.model.response.GamePoster
import elravi.compose.browsemygame.infra.ui.Event
import elravi.compose.browsemygame.ui.UiState
import elravi.compose.browsemygame.ui.UiState.Companion.getSuccessData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GameDetailViewModel(
    private val gameDetailUseCase: GameDetailUseCase,
    private val favoriteGameUseCase: FavoriteGameUseCase,
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _gameDetailState = MutableStateFlow<UiState<GameDetail>>(UiState.default())
    val gameDetailState: StateFlow<UiState<GameDetail>> = _gameDetailState

    fun getGameDetail(id: Int) {
        _gameDetailState.value = UiState.loading()
        viewModelScope.launch(ioDispatchers) {
            gameDetailUseCase.getGameDetail(id)
                .catch { ex ->
                    _gameDetailState.value = UiState.fail(ex, ex.message)
                }
                .collect {
                    _gameDetailState.value = UiState.success(it)
                }
        }
    }

    private val _gamePosterState = MutableStateFlow<UiState<List<GamePoster>>>(UiState.default())
    val gamePosterState: StateFlow<UiState<List<GamePoster>>> = _gamePosterState

    fun getGamePoster(id: Int) {
        _gamePosterState.value = UiState.loading()
        viewModelScope.launch(ioDispatchers) {
            gameDetailUseCase.getGamePoster(id)
                .catch { ex ->
                    _gamePosterState.value = UiState.fail(ex, ex.message)
                }
                .collect {
                    _gamePosterState.value = UiState.success(it)
                }
        }
    }

    private val _addFavGameState = MutableStateFlow<Event<UiState<Int>>>(Event(UiState.default()))
    val addFavGameState: StateFlow<Event<UiState<Int>>> = _addFavGameState

    fun addFavGame(gameDetail: GameDetail) {
        viewModelScope.launch(ioDispatchers) {
            val isSaveGameSuccess = favoriteGameUseCase.addFavoriteGame(gameDetail)
            if (isSaveGameSuccess) {
                _addFavGameState.value = Event(UiState.success(gameDetail.id))
            } else {
                _addFavGameState.value = Event(UiState.fail(null))
            }
        }
    }

    private val _removeFavGameState = MutableStateFlow<Event<UiState<Int>>>(Event(UiState.default()))
    val removeFavGameState: StateFlow<Event<UiState<Int>>> = _removeFavGameState

    fun removeFavGame(gameId: Int) {
        viewModelScope.launch(ioDispatchers) {
            val isRemoveFavGameSuccess = favoriteGameUseCase.removeFavoriteGame(gameId)
            if (isRemoveFavGameSuccess) {
                _removeFavGameState.value = Event(UiState.success(gameId))
            } else {
                _removeFavGameState.value = Event(UiState.fail(null))
            }
        }
    }

    fun updateGameDetailFavoriteStatus(isFavorite: Boolean) {
        _gameDetailState.value.getSuccessData()?.let {
            _gameDetailState.value = UiState.success(it.copy(isFavorite = isFavorite))
        }
    }
}