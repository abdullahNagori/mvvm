package com.example.abl.model

data class CheckinModel(
    val account_num: String,
    val visit_status: String,
    val visit_type: String,
    val followup_date: String,
    val date_of_conv: String,
    val customer_id: String,
    val lead_id: String,
    val product_id: String,
    val product_name: String,
    val amount: String,
    val comment: String,
    val visited_latitude: String,
    val visited_longitude: String
)