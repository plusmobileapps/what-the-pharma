package com.plusmobileapps.wtpharma

import androidx.compose.runtime.*

class GameViewModel {
    private var _gameState by mutableStateOf(GameState())
    val gameState: GameState get() = _gameState

    private var _currentScreen by mutableStateOf(GameScreen.START)
    val currentScreen: GameScreen get() = _currentScreen

    fun startGame() {
        _gameState = GameState()
        _currentScreen = GameScreen.GAME
    }

    fun answerQuestion(userAnswer: AnswerType) {
        val currentQuestion = GameData.questions[_gameState.currentQuestionIndex]
        val isCorrect = userAnswer == currentQuestion.correctAnswer
        val smeckleChange = if (isCorrect) 100 else -50

        val responseMessage = if (isCorrect) {
            GameData.getCorrectAnswerMessage(true)
        } else {
            GameData.getRandomMessage(currentQuestion.correctAnswer)
        }

        val questionResult = QuestionResult(
            question = currentQuestion,
            userAnswer = userAnswer,
            isCorrect = isCorrect,
            responseMessage = responseMessage
        )

        _gameState = _gameState.copy(
            smeckles = (_gameState.smeckles + smeckleChange).coerceAtLeast(0),
            questionsAnswered = _gameState.questionsAnswered + questionResult
        )
    }

    fun nextQuestion() {
        if (_gameState.currentQuestionIndex < GameData.questions.size - 1) {
            _gameState = _gameState.copy(
                currentQuestionIndex = _gameState.currentQuestionIndex + 1
            )
        }
    }

    fun completeGame() {
        _gameState = _gameState.copy(isGameComplete = true)
        _currentScreen = GameScreen.RESULTS
    }

    fun restartGame() {
        _gameState = GameState()
        _currentScreen = GameScreen.START
    }
}

enum class GameScreen {
    START, GAME, RESULTS
}
