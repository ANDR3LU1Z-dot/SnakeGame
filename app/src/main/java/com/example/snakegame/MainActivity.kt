package com.example.snakegame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snakegame.ui.theme.DarkGreen
import com.example.snakegame.ui.theme.Shapes
import com.example.snakegame.ui.theme.SnakeGameTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    val initialScreen = InitialScreen()
    val gameOverScreen = GameOverScreen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SnakeGameTheme {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                val game = remember { Game(scope) }

                LaunchedEffect(game) {
                    game.gameOver.collect { isGameOver ->
                        if (isGameOver) {
                            navController.navigate(Routes.GAME_OVER_SCREEN)
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ManagerScreens(navController, game)
                }
            }
        }
    }

    @Composable
    fun ManagerScreens(navController: NavHostController, game: Game) {

        NavHost(navController = navController, startDestination = Routes.INITIAL_SCREEN) {
            composable(Routes.INITIAL_SCREEN) {
                initialScreen.StartScreen(navController)
            }

            composable(Routes.SNAKE_GAME_SCREEN) {
                Snake(game)
            }

            composable(Routes.GAME_OVER_SCREEN) {
                gameOverScreen.OnGameOverScreen(
                    onRestart = {
                        game.resetGame()
                        navController.popBackStack()
                        navController.navigate(Routes.SNAKE_GAME_SCREEN) {
                            popUpTo(Routes.SNAKE_GAME_SCREEN) { inclusive = true }
                        }
                    })
            }
        }
    }

    @Composable
    fun Snake(game: Game) {
        val state = game.state.collectAsState(initial = null)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            state.value?.let {
                Board(it)
            }

            Buttons {
                game.move = it
            }
        }
    }

    @Composable
    fun Buttons(onDirectionChange: (Pair<Int, Int>) -> Unit) {
        val buttonsSize = Modifier.size(64.dp)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Button(onClick = { onDirectionChange(Pair(0, -1)) }, modifier = buttonsSize) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }

            Row {
                Button(onClick = { onDirectionChange(Pair(-1, 0)) }, modifier = buttonsSize) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null)
                }
                Spacer(modifier = buttonsSize)
                Button(onClick = { onDirectionChange(Pair(1, 0)) }, modifier = buttonsSize) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
                }

            }

            Button(onClick = { onDirectionChange(Pair(0, 1)) }, modifier = buttonsSize) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }
    }

    @SuppressLint("UnusedBoxWithConstraintsScope")
    @Composable
    fun Board(state: State) {

        BoxWithConstraints(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(26.dp)
        ) {
            val tileSize = maxWidth / Game.BOARD_SIZE
            Box(
                Modifier
                    .size(maxWidth)
                    .border(2.dp, DarkGreen)

            )

            Box(
                Modifier
                    .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                    .size(tileSize)
                    .background(
                        DarkGreen,
                        CircleShape
                    )
            )

            state.snake.forEach {
                Box(
                    modifier = Modifier
                        .offset(x = tileSize * it.first, y = tileSize * it.second)
                        .size(tileSize)
                        .background(
                            DarkGreen, Shapes.small
                        )
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SnakeGameTheme {
        }
    }

}



