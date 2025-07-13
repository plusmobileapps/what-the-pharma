package com.plusmobileapps.wtpharma

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = remember { GameViewModel() }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            when (viewModel.currentScreen) {
                GameScreen.START -> {
                    StartScreen(
                        onStartGame = { viewModel.startGame() }
                    )
                }

                GameScreen.GAME -> {
                    GameScreen(
                        gameState = viewModel.gameState,
                        onAnswerSelected = { answer -> viewModel.answerQuestion(answer) },
                        onNextQuestion = { viewModel.nextQuestion() },
                        onGameComplete = { viewModel.completeGame() }
                    )
                }

                GameScreen.RESULTS -> {
                    ResultsScreen(
                        gameState = viewModel.gameState,
                        onRestartGame = { viewModel.restartGame() }
                    )
                }
            }
        }
    }
}