package eu.tonkov.quizappcompose

data class Question(val question: String, val options: List<String>, val correctAnswerIndex: Int)
{
    init {
       if (correctAnswerIndex > 3) {
           throw IllegalArgumentException("Correct answer index must be between 0 and 3")

       }
    }
}