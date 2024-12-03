package com.example.quizcreator.models

data class Question(
    val questionText: String = "",
    val options: List<String> = listOf(),
    val correctOptionIndex: Int = -1
)