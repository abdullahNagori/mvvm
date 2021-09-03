package com.example.abl.model

data class SubmitQuizModel(
    var quiz_id: String,
    val quiz_details: ArrayList<QuizDetailItem>
)