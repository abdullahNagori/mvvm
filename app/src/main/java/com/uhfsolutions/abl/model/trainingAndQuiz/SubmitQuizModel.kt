package com.uhfsolutions.abl.model.trainingAndQuiz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubmitQuizModel(
    var quiz_id: String,
    val quiz_details: ArrayList<QuizDetailItem>
): Parcelable