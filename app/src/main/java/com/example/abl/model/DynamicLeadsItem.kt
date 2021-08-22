package com.example.abl.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Lead")
data class DynamicLeadsItem(
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "age")
    val age: String,
    val auto_assignment: String,
    @ColumnInfo(name = "branch_code")
    val branch_code: String,
    @ColumnInfo(name = "branch_name")
    val branch_name: String,
    @ColumnInfo(name = "campaign_id")
    val campaign_id: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "comment")
    val comment: String,
    @ColumnInfo(name = "company_id")
    val company_id: String,
    @ColumnInfo(name = "created_at")
    val created_at: String,
    @ColumnInfo(name = "created_by")
    val created_by: String,
    @ColumnInfo(name = "crm_lead_id")
    val crm_lead_id: String,
    @ColumnInfo(name = "crm_order_id")
    val crm_order_id: String,
    @ColumnInfo(name = "crm_update_id")
    val crm_update_id: String,
    @ColumnInfo(name = "customer_id")
    val customer_id: String,
    @ColumnInfo(name = "department")
    val department: String,
    @ColumnInfo(name = "email_address")
    val email_address: String,
    @ColumnInfo(name = "employment")
    val employment: String,
    @ColumnInfo(name = "expected_amount")
    val expected_amount: String,
    @ColumnInfo(name = "expected_closure_date")
    val expected_closure_date: String,
    @ColumnInfo(name = "first_name")
    val first_name: String,
    @ColumnInfo(name = "follow_up_date")
    val follow_up_date: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "home_phone_number")
    val home_phone_number: String,
    @ColumnInfo(name = "is_closed")
    val is_closed: String,
    @ColumnInfo(name = "job_title")
    val job_title: String,
    @ColumnInfo(name = "last_name")
    val last_name: String,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "lead_status")
    val lead_status: String,
    @ColumnInfo(name = "lead_status_name")
    val lead_status_name: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,
    @ColumnInfo(name = "marital_status")
    val marital_status: String,
    @ColumnInfo(name = "mobile_phone_number")
    val mobile_phone_number: String,
    @ColumnInfo(name = "near_by_location")
    val near_by_location: String,
    @ColumnInfo(name = "office_phone_number")
    val office_phone_number: String,
    @ColumnInfo(name = "potential_amount")
    val potential_amount: String,
    @ColumnInfo(name = "probability")
    val probability: String,
    @ColumnInfo(name = "product_id")
    val product_id: String,
    @ColumnInfo(name = "product_name")
    val product_name: String,
    @ColumnInfo(name = "record_id")
    val record_id: String,
    @ColumnInfo(name = "region")
    val region: String,
    @ColumnInfo(name = "source")
    val source: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "transaction_id")
    val transaction_id: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "updated_at")
    val updated_at: String,
    @ColumnInfo(name = "user_code")
    val user_code: String,
    @ColumnInfo(name = "user_id")
    val user_id: String,
    @ColumnInfo(name = "user_name")
    val user_name: String,
    @ColumnInfo(name = "lead_id")
    val lead_id: String
):Parcelable

{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var Id: Int? = null
}