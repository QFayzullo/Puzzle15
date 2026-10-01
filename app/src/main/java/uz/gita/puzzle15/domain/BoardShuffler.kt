package uz.gita.puzzle15.domain

import kotlin.random.Random

class BoardShuffler(
    private val random: Random = Random.Default
) {

    fun shuffle(
        size: Int = 4,
        moves: Int = size * size * 20
    ): Board {
        var board: Board
        do {
            board = scramble(size, moves)
        } while (board.isSolved)
        return board
    }

    private fun scramble(size: Int, moves: Int): Board {
        var board = Board.solved(size)
        var previousEmpty = -1

        repeat(moves) {
            val empty = board.emptyIndex
            val next = neighbors(empty, size)
                .filter { it != previousEmpty }
                .random(random)

            board = board.move(next)
            previousEmpty = empty
        }
        return board
    }

    private fun neighbors(index: Int, size: Int): List<Int> {
        val row = index / size
        val column = index % size
        return buildList {
            if (row > 0) add(index - size)
            if (row < size - 1) add(index + size)
            if (column > 0) add(index - 1)
            if (column < size - 1) add(index + 1)
        }
    }
}