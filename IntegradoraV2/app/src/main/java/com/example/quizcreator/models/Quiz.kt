package com.example.quizcreator.models

data class Quiz(
    val quizId: String = "", // Identificador único del quiz
    val title: String = "",
    val questions: List<Question> = emptyList()
) {
    data class Question(
        val questionText: String = "",
        val option1: String = "",
        val option2: String = "",
        val option3: String = "",
        val options: List<String> = listOf(),
        val correctOption: String = "",
        val correctOptionIndex: Int = -1
    )
}
