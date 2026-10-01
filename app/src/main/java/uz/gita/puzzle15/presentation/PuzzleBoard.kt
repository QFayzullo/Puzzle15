package uz.gita.puzzle15.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import uz.gita.puzzle15.domain.Board

@Composable
fun PuzzleBoard(
    board: Board,
    enabled: Boolean,
    onTileClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = 8.dp

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(elevation = 12.dp, shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(spacing)
    ) {
        val size = board.size
        val tileSize = (maxWidth - spacing * (size - 1)) / size

        board.tiles.forEachIndexed { index, number ->
            if (number != Board.EMPTY) {
                key(number) {
                    val x by animateDpAsState(
                        targetValue = (tileSize + spacing) * (index % size),
                        animationSpec = tween(durationMillis = 150),
                        label = "tileX"
                    )
                    val y by animateDpAsState(
                        targetValue = (tileSize + spacing) * (index / size),
                        animationSpec = tween(durationMillis = 150),
                        label = "tileY"
                    )
                    TileItem(
                        number = number,
                        isInPlace = number == index + 1,
                        size = tileSize,
                        enabled = enabled,
                        onClick = { onTileClick(index) },
                        modifier = Modifier.offset {
                            IntOffset(x.roundToPx(), y.roundToPx())
                        }
                    )
                }
            }
        }
    }
}