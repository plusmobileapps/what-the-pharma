package com.plusmobileapps.wtpharma

data class Question(
    val productName: String,
    val correctAnswer: AnswerType
)

enum class AnswerType {
    DRUG, AMAZON
}

data class GameState(
    val currentQuestionIndex: Int = 0,
    val smeckles: Int = 0,
    val questionsAnswered: List<QuestionResult> = emptyList(),
    val isGameComplete: Boolean = false
)

data class QuestionResult(
    val question: Question,
    val userAnswer: AnswerType,
    val isCorrect: Boolean,
    val responseMessage: String
)

object GameData {
    val questions = listOf(
        Question("Xdemvy", AnswerType.DRUG),
        Question("Ubrelvy", AnswerType.DRUG),
        Question("zepbound", AnswerType.DRUG),
        Question("opzelura", AnswerType.DRUG),
        Question("ebglyss", AnswerType.DRUG),
        Question("wegovy", AnswerType.DRUG),
        Question("airsupra", AnswerType.DRUG),
        Question("skyrizi", AnswerType.DRUG),
        Question("jadoal", AnswerType.AMAZON),
        Question("zyxel", AnswerType.AMAZON),
        Question("palksky", AnswerType.AMAZON),
        Question("Ellasay", AnswerType.AMAZON),
        Question("miradexic", AnswerType.AMAZON),
        Question("fyndrax", AnswerType.AMAZON),
        Question("austedo", AnswerType.DRUG)
    )

    private val amazonMessages = listOf(
        "I'd give this zero stars if I could because it burned my house down.",
        "Save 14% on Prime Day.",
        "Rated 4.9 stars by 4500 fake reviewers."
    )

    private val drugMessages = listOf(
        "Side effects include excess earwax production and uncontrollable flatulence",
        "Do not take this if you've recently been exposed to dog hair",
        "Clinical trials were rubber stamped by an overworked FDA official."
    )

    fun getRandomMessage(correctAnswer: AnswerType): String {
        return when (correctAnswer) {
            AnswerType.AMAZON -> amazonMessages.random()
            AnswerType.DRUG -> drugMessages.random()
        }
    }

    fun getCorrectAnswerMessage(isCorrect: Boolean): String {
        return if (isCorrect) {
            "Correct! +100 smeckles"
        } else {
            "Wrong! -50 smeckles"
        }
    }
}
