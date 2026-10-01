package uz.gita.puzzle15.data

import android.content.Context
import uz.gita.puzzle15.domain.Board
import uz.gita.puzzle15.domain.GameStorage
import uz.gita.puzzle15.domain.SavedGame

class PrefsGameStorage(context: Context) : GameStorage {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun save(game: SavedGame) {
        prefs.edit()
            .putInt(KEY_SIZE, game.board.size)
            .putString(KEY_TILES, game.board.tiles.joinToString(SEPARATOR))
            .putString(KEY_HISTORY, game.history.joinToString(SEPARATOR))
            .apply()
    }

    override fun load(): SavedGame? = runCatching {
        val size = prefs.getInt(KEY_SIZE, 0)
        val tiles = prefs.getString(KEY_TILES, null).toIntList()
        val history = prefs.getString(KEY_HISTORY, null).toIntList()

        require(tiles.sorted() == List(size * size) { it }) { "Taxta buzilgan" }
        require(history.all { it in tiles.indices }) { "Tarix buzilgan" }

        SavedGame(Board(size, tiles), history)
    }.getOrNull()

    override fun clear() {
        prefs.edit()
            .remove(KEY_SIZE)
            .remove(KEY_TILES)
            .remove(KEY_HISTORY)
            .apply()
    }

    private fun String?.toIntList(): List<Int> =
        if (isNullOrEmpty()) emptyList() else split(SEPARATOR).map { it.toInt() }

    private companion object {
        const val PREFS_NAME = "puzzle15_game"
        const val KEY_SIZE = "size"
        const val KEY_TILES = "tiles"
        const val KEY_HISTORY = "history"
        const val SEPARATOR = ","
    }
}