package com.example.abl.model

data class VisitsCallResponseItem(
    val customer_name: String,
    val mobile_phone_number: String,
    val product_name: String,
    val user_name: String,
    val visit_date_time: String,
    val visit_status: String,
    val visit_type: String
)