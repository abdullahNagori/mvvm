package com.example.abl.model.trainingAndQuiz

import android.os.Parcelable
import com.example.abl.model.trainingAndQuiz.QuizDetailItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubmitQuizModel(
    var quiz_id: String,
    val quiz_details: ArrayList<QuizDetailItem>
): Parcelable