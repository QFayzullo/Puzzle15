package uz.gita.puzzle15.domain

data class SavedGame(
    val board: Board,
    val history: List<Int>
)

interface GameStorage {
    fun save(game: SavedGame)
    fun load(): SavedGame?
    fun clear()
}