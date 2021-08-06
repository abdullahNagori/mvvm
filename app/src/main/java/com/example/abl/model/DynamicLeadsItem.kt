package com.example.abl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class DynamicLeadsItem(
    val address: String,
    val age: String,
    val auto_assignment: String,
    val branch_code: String,
    val branch_name: String,
    val campaign_id: String,
    val city: String,
    val comment: String,
    val company_id: String,
    val created_at: String,
    val created_by: String,
    val crm_lead_id: String,
    val crm_order_id: String,
    val crm_update_id: String,
    val customer_id: String,
    val department: String,
    val email_address: String,
    val employment: String,
    val expected_amount: String,
    val expected_closure_date: String,
    val first_name: String,
    val follow_up_date: String,
    val gender: String,
    val home_phone_number: String,
    val is_closed: String,
    val job_title: String,
    val last_name: String,
    val latitude: String,
    val lead_status: String,
    val lead_status_name: String,
    val longitude: String,
    val marital_status: String,
    val mobile_phone_number: String,
    val near_by_location: String,
    val office_phone_number: String,
    val potential_amount: String,
    val probability: String,
    val product_id: String,
    val product_name: String,
    val record_id: String,
    val region: String,
    val source: String,
    val status: String,
    val transaction_id: String,
    val type: String,
    val updated_at: String,
    val user_code: String,
    val user_id: String,
    val user_name: String,
    val lead_id: String
):Parcelable