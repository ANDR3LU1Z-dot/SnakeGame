package com.example.snakegame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class GameOverScreen {

    @Composable
    fun OnGameOverScreen(onRestart: () -> Unit, onBackToMenu: () -> Unit, mediaPlayer: MediaPlayerHelper = rememberMediaPlayer()) {

        DisposableEffect(Unit) {
            mediaPlayer.play(R.raw.game_over, loop = false)

            onDispose {
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.game_over_logo),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onRestart) {
                    Text("Play Again")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBackToMenu) {
                    Text("Back to Main Menu")
                }
            }
        }
    }
}