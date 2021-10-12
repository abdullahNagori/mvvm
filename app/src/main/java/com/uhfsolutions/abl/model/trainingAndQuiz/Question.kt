package com.uhfsolutions.abl.model.trainingAndQuiz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val correct_ans: String,
    val option_1: String,
    val option_2: String,
    val option_3: String,
    val option_4: String,
    val options: List<Option>,
    val question: String,
    val quiz_id: String,
    val record_id: String,
    val status: String
): Parcelable