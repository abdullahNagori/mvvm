package com.example.abl.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import javax.annotation.Nullable

@Parcelize
@Entity(tableName = "Lead")
data class DynamicLeadsItem(
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "age")
    val age: String?,
    val branch_code: String?,
    val branch_name: String?,
    @ColumnInfo(name = "campaign_id")
    val campaign_id: String?,
    @ColumnInfo(name = "city")
    val city: String?,
    val comment: String?,
    val company_id: String?,
    val created_at: String?,
    val created_by: String?,
    val crm_lead_id: String?,
    val crm_order_id: String?,
    val crm_update_id: String?,
    val customer_id: String?,
    @ColumnInfo(name = "department")
    val department: String?,
    @ColumnInfo(name = "email_address")
    val email_address: String?,
    @ColumnInfo(name = "employment")
    val employment: String?,
    @ColumnInfo(name = "expected_amount")
    val expected_amount: String?,
    @ColumnInfo(name = "expected_closure_date")
    val expected_closure_date: String?,
    @ColumnInfo(name = "first_name")
    val first_name: String?,
    val follow_up_date: String?,
    @ColumnInfo(name = "gender")
    val gender: String?,
    @ColumnInfo(name = "home_phone_number")
    val home_phone_number: String?,
    val is_closed: String?,
    @ColumnInfo(name = "job_title")
    val job_title: String?,
    @ColumnInfo(name = "last_name")
    val last_name: String?,
    val latitude: String?,
    @ColumnInfo(name = "lead_status")
    val lead_status: String?,
    @ColumnInfo(name = "lead_status_name")
    val lead_status_name: String?,
    val longitude: String?,
    @ColumnInfo(name = "marital_status")
    val marital_status: String?,
    @ColumnInfo(name = "mobile_phone_number")
    val mobile_phone_number: String?,
    val near_by_location: String?,
    @ColumnInfo(name = "office_phone_number")
    val office_phone_number: String?,
    @ColumnInfo(name = "potential_amount")
    val potential_amount: String?,
    @ColumnInfo(name = "probability")
    val probability: String?,
    @ColumnInfo(name = "product_id")
    val product_id: String?,
    @ColumnInfo(name = "product_name")
    val product_name: String?,
    val record_id: String?,
    @ColumnInfo(name = "region")
    val region: String?,
    @ColumnInfo(name = "source")
    val source: String?,
    val status: String?,
    val transaction_id: String?,
    val type: String?,
    val updated_at: String?,
    val user_code: String?,
    val user_id: String?,
    val user_name: String?,
    @ColumnInfo(name = "lead_id")
    val lead_id: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "desc")
    val desc: String?
):Parcelable

{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var Id: Int? = null
}