package com.plusmobileapps.wtpharma.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.plusmobileapps.wtpharma.*

@Composable
fun ResultsScreen(
    gameState: GameState,
    onRestartGame: () -> Unit
) {
    val correctAnswers = gameState.questionsAnswered.count { it.isCorrect }
    val totalQuestions = gameState.questionsAnswered.size
    val wonPrize = gameState.smeckles >= 1000

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Game Complete!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (wonPrize)
                    MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Final Score",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "${gameState.smeckles} smeckles",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (wonPrize)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Correct: $correctAnswers/$totalQuestions",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (wonPrize) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                    Text(
                        text = "🎉 CONGRATULATIONS! 🎉",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "You've won the ultimate prize for watching the most American ad commercials!",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Your reward: A lifetime supply of pharmaceutical side effects and sketchy Amazon products!",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "You needed 1000 smeckles to win the prize. Better luck next time!",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }

        Button(
            onClick = onRestartGame,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Play Again",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ResultsScreenWinnerPreview() {
    MaterialTheme {
        Surface {
            ResultsScreen(
                gameState = GameState(
                    currentQuestionIndex = 14,
                    smeckles = 1200,
                    questionsAnswered = List(15) { index ->
                        QuestionResult(
                            question = GameData.questions[index],
                            userAnswer = GameData.questions[index].correctAnswer,
                            isCorrect = index < 13, // 13 correct, 2 wrong
                            responseMessage = if (index < 13) "Correct!" else "Wrong!"
                        )
                    }
                ),
                onRestartGame = {}
            )
        }
    }
}

@Preview
@Composable
fun ResultsScreenLoserPreview() {
    MaterialTheme {
        Surface {
            ResultsScreen(
                gameState = GameState(
                    currentQuestionIndex = 14,
                    smeckles = 750,
                    questionsAnswered = List(15) { index ->
                        QuestionResult(
                            question = GameData.questions[index],
                            userAnswer = GameData.questions[index].correctAnswer,
                            isCorrect = index < 10, // 10 correct, 5 wrong
                            responseMessage = if (index < 10) "Correct!" else "Wrong!"
                        )
                    }
                ),
                onRestartGame = {}
            )
        }
    }
}
