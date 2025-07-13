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
fun GameScreen(
    gameState: GameState,
    onAnswerSelected: (AnswerType) -> Unit,
    onNextQuestion: () -> Unit,
    onGameComplete: () -> Unit
) {
    val currentQuestion = GameData.questions[gameState.currentQuestionIndex]
    val hasAnswered = gameState.questionsAnswered.any { it.question == currentQuestion }
    val currentResult = gameState.questionsAnswered.find { it.question == currentQuestion }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Smeckles counter
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (gameState.smeckles >= 1000)
                    MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = "Smeckles: ${gameState.smeckles}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        // Progress indicator
        Text(
            text = "Question ${gameState.currentQuestionIndex + 1} of ${GameData.questions.size}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Question card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "What is:",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = currentQuestion.productName,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (hasAnswered && currentResult != null) {
                    // Show answer result
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                    Text(
                        text = "Your answer: ${if (currentResult.userAnswer == AnswerType.DRUG) "Prescription Drug" else "Amazon Brand"}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = if (currentResult.isCorrect) "✓ Correct!" else "✗ Wrong!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentResult.isCorrect)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    if (!currentResult.isCorrect) {
                        Text(
                            text = currentResult.responseMessage,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    Text(
                        text = if (currentResult.isCorrect) "+100 smeckles" else "-50 smeckles",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentResult.isCorrect)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        if (!hasAnswered) {
            // Answer buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onAnswerSelected(AnswerType.DRUG) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Prescription Drug",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { onAnswerSelected(AnswerType.AMAZON) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Amazon Brand",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            // Next/Complete button
            Button(
                onClick = {
                    if (gameState.currentQuestionIndex < GameData.questions.size - 1) {
                        onNextQuestion()
                    } else {
                        onGameComplete()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (gameState.currentQuestionIndex < GameData.questions.size - 1)
                        "Next Question"
                    else "Complete Game",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    MaterialTheme {
        Surface {
            GameScreen(
                gameState = GameState(
                    currentQuestionIndex = 0,
                    smeckles = 250,
                    questionsAnswered = emptyList()
                ),
                onAnswerSelected = {},
                onNextQuestion = {},
                onGameComplete = {}
            )
        }
    }
}

@Preview
@Composable
fun GameScreenAnsweredPreview() {
    MaterialTheme {
        Surface {
            GameScreen(
                gameState = GameState(
                    currentQuestionIndex = 0,
                    smeckles = 350,
                    questionsAnswered = listOf(
                        QuestionResult(
                            question = GameData.questions[0],
                            userAnswer = AnswerType.AMAZON,
                            isCorrect = false,
                            responseMessage = "I'd give this zero stars if I could because it burned my house down."
                        )
                    )
                ),
                onAnswerSelected = {},
                onNextQuestion = {},
                onGameComplete = {}
            )
        }
    }
}
