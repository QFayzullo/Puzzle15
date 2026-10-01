package uz.gita.puzzle15.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.gita.puzzle15.R
import uz.gita.puzzle15.domain.Board
import uz.gita.puzzle15.ui.theme.Puzzle15Theme

@Composable
fun PuzzleScreen(viewModel: PuzzleViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    PuzzleContent(
        state = state,
        onTileClick = viewModel::onTileClick,
        onUndo = viewModel::onUndo,
        onNewGame = viewModel::onNewGame,
        onDismissWinDialog = viewModel::onDismissWinDialog
    )
}

@Composable
private fun PuzzleContent(
    state: PuzzleUiState,
    onTileClick: (Int) -> Unit,
    onUndo: () -> Unit,
    onNewGame: () -> Unit,
    onDismissWinDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(colors.background, colors.primaryContainer)))
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Header()
            MovesChip(moves = state.moves)

            PuzzleBoard(
                board = state.board,
                enabled = !state.isSolved,
                onTileClick = onTileClick,
                modifier = Modifier.fillMaxWidth()
            )

            Actions(
                canUndo = state.canUndo,
                onUndo = onUndo,
                onNewGame = onNewGame
            )
        }
    }

    if (state.showWinDialog) {
        WinDialog(
            moves = state.moves,
            onNewGame = onNewGame,
            onDismiss = onDismissWinDialog
        )
    }
}

@Composable
private fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.puzzle_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.puzzle_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MovesChip(moves: Int) {
    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.moves_title),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = moves.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun Actions(
    canUndo: Boolean,
    onUndo: () -> Unit,
    onNewGame: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onUndo,
            enabled = canUndo,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
        ) {
            Text(stringResource(R.string.undo))
        }
        Button(
            onClick = onNewGame,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
        ) {
            Text(stringResource(R.string.new_game))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PuzzleContentPreview() {
    Puzzle15Theme {
        PuzzleContent(
            state = PuzzleUiState(board = Board.solved().move(14), history = listOf(15)),
            onTileClick = {},
            onUndo = {},
            onNewGame = {},
            onDismissWinDialog = {}
        )
    }
}