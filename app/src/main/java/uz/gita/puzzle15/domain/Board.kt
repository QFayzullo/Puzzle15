package uz.gita.puzzle15.domain

import kotlin.math.abs

data class Board(
    val size: Int,
    val tiles: List<Int>
) {
    init {
        require(size >= 2) { "size kamida 2 bo'lsin" }
        require(tiles.size == size * size) { "tile lar  soni size*size ga teng bo'lsin" }
    }

    val emptyIndex: Int get() = tiles.indexOf(EMPTY)

    val isSolved: Boolean get() = tiles == solvedTiles(size)

    fun canMove(index: Int): Boolean {
        if (index !in tiles.indices || index == emptyIndex) return false
        val empty = emptyIndex
        val sameRow = index / size == empty / size
        val sameColumn = index % size == empty % size
        return (sameRow && abs(index - empty) == 1) ||
            (sameColumn && abs(index - empty) == size)
    }

    fun move(index: Int): Board {
        if (!canMove(index)) return this
        val newTiles = tiles.toMutableList()
        newTiles[emptyIndex] = newTiles[index]
        newTiles[index] = EMPTY
        return copy(tiles = newTiles)
    }

    companion object {
        const val EMPTY = 0

        fun solved(size: Int = 4): Board = Board(size, solvedTiles(size))

        private fun solvedTiles(size: Int): List<Int> =
            List(size * size) { if (it == size * size - 1) EMPTY else it + 1 }
    }
}