package uz.gita.puzzle15.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import uz.gita.puzzle15.domain.BoardShuffler
import uz.gita.puzzle15.domain.GameStorage
import uz.gita.puzzle15.domain.SavedGame

class PuzzleViewModel(
    private val storage: GameStorage,
    private val shuffler: BoardShuffler = BoardShuffler(),
    private val size: Int = 4
) : ViewModel() {

    private val _uiState = MutableStateFlow(restoreOrCreateGame())
    val uiState: StateFlow<PuzzleUiState> = _uiState.asStateFlow()

    fun onTileClick(index: Int) {
        val state = _uiState.value
        if (state.isSolved || !state.board.canMove(index)) return

        val newBoard = state.board.move(index)
        updateState(
            state.copy(
                board = newBoard,
                history = state.history + state.board.emptyIndex,
                isSolved = newBoard.isSolved,
                showWinDialog = newBoard.isSolved
            )
        )
    }

    fun onUndo() {
        val state = _uiState.value
        if (!state.canUndo) return

        updateState(
            state.copy(
                board = state.board.move(state.history.last()),
                history = state.history.dropLast(1)
            )
        )
    }

    fun onNewGame() = updateState(newGame())

    fun onDismissWinDialog() {
        _uiState.update { it.copy(showWinDialog = false) }
    }

    private fun updateState(state: PuzzleUiState) {
        _uiState.value = state
        persist(state)
    }

    private fun persist(state: PuzzleUiState) {
        if (state.isSolved) {
            storage.clear()
        } else {
            storage.save(SavedGame(state.board, state.history))
        }
    }

    private fun restoreOrCreateGame(): PuzzleUiState {
        val saved = storage.load()?.takeIf { it.board.size == size && !it.board.isSolved }
        return if (saved != null) {
            PuzzleUiState(board = saved.board, history = saved.history)
        } else {
            newGame().also { persist(it) }
        }
    }

    private fun newGame() = PuzzleUiState(board = shuffler.shuffle(size))
}