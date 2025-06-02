package com.example.snakegame

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

class InitialScreen {

    @Composable
    fun StartScreen(navController: NavController, mediaPlayer: MediaPlayerHelper = rememberMediaPlayer()) {

        DisposableEffect(Unit) {
            mediaPlayer.play(R.raw.main_menu)

            onDispose {
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.snake_game_logo),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )

            Button(modifier = Modifier.padding(top = 60.dp),
                onClick = { navController.navigate(Routes.SNAKE_GAME_SCREEN) }) {
                Text("Start Game")
            }
        }
    }
}