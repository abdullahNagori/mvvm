package com.example.abl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Training(
    val attendance: String,
    val company_id: String,
    val conducted_by: String,
    val created_at: String,
    val end_date: String,
    val end_time: String,
    val is_deleted: String,
    val materials: List<Material>,
    val record_id: String,
    val start_date: String,
    val start_time: String,
    val status: String,
    val training_name: String,
    val training_type: String,
    val user_id: String,
    val venue: String

): Parcelable