package com.example.abl.model

data class Quiz(
    val desc: String,
    val no_of_questions: String,
    val questions: List<Question>,
    val quiz_name: String,
    val record_id: String,
    val status: String,
    val time: String,
    val training_id: String
)