package com.example.abl.model

import kotlinx.serialization.Serializable


data class CompanyVisitStatu(
    val company_id: String,
    val created_at: String,
    val created_by: String,
    val name: String,
    val record_id: String,
    val status: String,
    val updated_at: String
)