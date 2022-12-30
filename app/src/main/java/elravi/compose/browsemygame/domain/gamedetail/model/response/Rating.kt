package elravi.compose.browsemygame.domain.gamedetail.model.response

data class Rating(
    val count: Int,
    val id: Int,
    val title: String,
    val percent: Double
)