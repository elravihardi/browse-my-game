package elravi.compose.browsemygame.domain.game

import elravi.compose.browsemygame.data.game.GameRepository
import elravi.compose.browsemygame.data.game.remote.model.request.GameDateRangeRequest
import elravi.compose.browsemygame.domain.game.mapper.toGameList
import elravi.compose.browsemygame.domain.game.model.response.gamelist.GameList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class GameUseCaseImpl(
    private val gameRepository: GameRepository
): GameUseCase {

    private val pageSize = 20
    private var dateRange: String? = null

    override fun getNewReleaseGame(page: Int): Flow<GameList> {
        return gameRepository.getNewReleaseGame(
                GameDateRangeRequest(
                    dates = dateRange ?: get2YearsDateRange().let {
                        dateRange = it
                        it
                    },
                    orderBy = "-addedd",
                    page = page.toString(),
                    pageSize = pageSize.toString()
                )
            )
            .map { gameList ->
                gameList.toGameList()
            }
    }

    private fun get2YearsDateRange(): String {
        fun format(number: Int) = if (number < 10) "0$number" else number.toString()

        val today = Calendar.getInstance()
        today.add(Calendar.YEAR, -1)

        val startYear = today.get(Calendar.YEAR)
        val startMonth = format(today.get(Calendar.MONTH) + 1)
        val startDate = format(today.get(Calendar.DATE))

        today.add(Calendar.YEAR, 3)
        val endYear = today.get(Calendar.YEAR)
        val endMonth = format(today.get(Calendar.MONTH) + 1)
        val endDate = format(today.get(Calendar.DATE))

        return "$startYear-$startMonth-$startDate,$endYear-$endMonth-$endDate"
    }
}