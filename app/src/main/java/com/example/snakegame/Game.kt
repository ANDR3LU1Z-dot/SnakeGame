package com.example.snakegame

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class Game(private val scope: CoroutineScope) {
    var gameRunning = true
    private val mutex = Mutex()
    private val _gameOver = MutableStateFlow(false)
    val gameOver: Flow<Boolean> = _gameOver
    var snakeLength = 4

    private val mutableState =
        MutableStateFlow(State(food = Pair(5, 5), snake = listOf(Pair(7, 7))))
    val state: Flow<State> = mutableState

    var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    init {
        scope.launch {
            while (true) {
                if (gameRunning) {
                    delay(150)
                    mutableState.update {
                        val newPosition = it.snake.first().let { poz ->
                            mutex.withLock {
                                Pair(
                                    (poz.first + move.first + BOARD_SIZE) % BOARD_SIZE,
                                    (poz.second + move.second + BOARD_SIZE) % BOARD_SIZE
                                )
                            }
                        }

                        if (newPosition == it.food) {
                            snakeLength++
                        }

                        if (it.snake.contains(newPosition)) {
                            _gameOver.value = true
                            gameRunning = false
                            return@update it
                        }

                        it.copy(
                            food = if (newPosition == it.food) Pair(
                                Random.nextInt(BOARD_SIZE),
                                Random.nextInt(BOARD_SIZE)
                            ) else it.food,
                            snake = listOf(newPosition) + it.snake.take(snakeLength - 1)
                        )
                    }
                } else {
                    delay(100)
                }
            }
        }
    }

    fun resetGame() {
        gameRunning = true
        _gameOver.value = false
        mutableState.value = State(food = Pair(5, 5), snake = listOf(Pair(7, 7)))
        move = Pair(1, 0)
        snakeLength = 4
    }

    companion object {
        const val BOARD_SIZE = 16
    }
}