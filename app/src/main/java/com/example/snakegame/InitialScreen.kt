package com.example.snakegame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

class InitialScreen {

    @Composable
    fun StartScreen(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Snake Game",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Button(onClick = { navController.navigate(Routes.SNAKE_GAME_SCREEN) }) {
                Text("Start Game")
            }
        }
    }
}