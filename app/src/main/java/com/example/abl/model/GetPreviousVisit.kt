package com.example.abl.model

data class GetPreviousVisit(
    val amount: String,
    val comment: String,
    val created_at: String,
    val date_of_conv: String,
    val lead_status_name: String,
    val product_id: String,
    val product_name: String,
    val visit_type: String,
    val visited_latitude: String,
    val visited_longitude: String,
    val visited_time: String
)