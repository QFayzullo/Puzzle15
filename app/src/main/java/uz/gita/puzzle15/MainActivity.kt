package uz.gita.puzzle15

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import uz.gita.puzzle15.data.PrefsGameStorage
import uz.gita.puzzle15.presentation.PuzzleScreen
import uz.gita.puzzle15.presentation.PuzzleViewModel
import uz.gita.puzzle15.ui.theme.Puzzle15Theme

class MainActivity : ComponentActivity() {

    private val viewModel: PuzzleViewModel by viewModels {
        viewModelFactory {
            initializer { PuzzleViewModel(storage = PrefsGameStorage(applicationContext)) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        setContent {
            Puzzle15Theme {
                PuzzleScreen(viewModel)
            }
        }
    }
}