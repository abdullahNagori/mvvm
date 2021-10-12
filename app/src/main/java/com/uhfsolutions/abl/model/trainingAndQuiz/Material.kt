package com.uhfsolutions.abl.model.trainingAndQuiz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Material(
    val created_at: String,
    val description: String,
    val material_name: String,
    val material_url: String,
    val record_id: String,
    val status: String,
    val title: String,
    val training_id: String,
    val updated_at: String

): Parcelable