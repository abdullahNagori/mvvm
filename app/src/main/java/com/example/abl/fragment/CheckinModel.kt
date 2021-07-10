package com.example.abl.fragment

data class CheckinModel(
    val account_num: String,
    val amount: String,
    val comment: String,
    val customer_id: String,
    val date_of_conv: String,
    val followup_date: String,
    val product_id: String,
    val product_name: String,
    val visit_status: String,
    val visit_type: String,
    val visited_latitude: String,
    val visited_longitude: String
)