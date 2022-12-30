package elravi.compose.browsemygame.domain.game.model.response.gamelist

data class GameList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Game>
)