package elravi.compose.browsemygame.domain.game.model.response.common

data class Store(
    val name: String,
    val id: Int,
    val slug: String,
    val domain: String
)