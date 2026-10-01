package uz.gita.puzzle15.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PuzzleColorScheme = lightColorScheme(
    primary = Navy,
    onPrimary = Mist,
    primaryContainer = Sky,
    onPrimaryContainer = Navy,
    secondary = Slate,
    onSecondary = Color.White,
    background = Mist,
    onBackground = Navy,
    surface = Mist,
    onSurface = Navy,
    outline = Slate
)

@Composable
fun Puzzle15Theme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = PuzzleColorScheme, content = content)
}