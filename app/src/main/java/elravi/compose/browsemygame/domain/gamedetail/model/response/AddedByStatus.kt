package elravi.compose.browsemygame.domain.gamedetail.model.response

data class AddedByStatus(
    val owned: Int,
    val beaten: Int,
    val dropped: Int,
    val yet: Int,
    val playing: Int,
    val toplay: Int
)