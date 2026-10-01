package uz.gita.puzzle15.presentation

import uz.gita.puzzle15.domain.Board

data class PuzzleUiState(
    val board: Board,
    val history: List<Int> = emptyList(),
    val isSolved: Boolean = false,
    val showWinDialog: Boolean = false
) {
    val moves: Int get() = history.size
    val canUndo: Boolean get() = history.isNotEmpty() && !isSolved
}