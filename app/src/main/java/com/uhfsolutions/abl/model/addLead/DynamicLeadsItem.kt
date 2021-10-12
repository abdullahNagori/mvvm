package com.uhfsolutions.abl.model.addLead

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uhfsolutions.abl.model.previousVisits.GetPreviousVisit
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Lead")
data class DynamicLeadsItem(

    @ColumnInfo(name = "first_name")
    val first_name: String?,
    @ColumnInfo(name = "cnic")
    val cnic: String?,
    @ColumnInfo(name = "es_income")
    val es_income: String?,
    @ColumnInfo(name = "occupation")
    val occupation: String?,
    @ColumnInfo(name = "source_of_income")
    val source_of_income: String?,
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "age")
    val age: String?,
    @ColumnInfo(name = "company_name")
    val company_name: String?,
    @ColumnInfo(name = "gender")
    val gender: String?,
    @ColumnInfo(name = "product_id")
    val product_id: String?,
    @ColumnInfo(name = "product_name")
    val product_name: String?,
    @ColumnInfo(name = "mobile_phone_number")
    val mobile_phone_number: String?,
    @ColumnInfo(name = "lead_status")
    val lead_status: String?,
    @ColumnInfo(name = "lead_status_name")
    val lead_status_name: String?,
    @ColumnInfo(name = "longitude")
    val longitude: String?,
    @ColumnInfo(name = "latitude")
    val latitude: String?,
    @ColumnInfo(name = "lead_id")
    val lead_id: String?,
    @NonNull
    @ColumnInfo(name = "local_lead_id")
    val local_lead_id: String?,
    @ColumnInfo(name = "marital_status")
    val marital_status: String?,
    @ColumnInfo(name = "office_phone_number")
    val office_phone_number: String?,
    @ColumnInfo(name = "potential_amount")
    val potential_amount: String?,
    @ColumnInfo(name = "probability")
    val probability: String?,
    @ColumnInfo(name = "region")
    val region: String?,
    @ColumnInfo(name = "source")
    val source: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "desc")
    val desc: String?,
    @ColumnInfo(name = "home_phone_number")
    val home_phone_number: String?,
    val is_closed: String?,
    @ColumnInfo(name = "job_title")
    val job_title: String?,
    @ColumnInfo(name = "campaign_id")
    val campaign_id: String?,
    @ColumnInfo(name = "city")
    val city: String?,
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
    @ColumnInfo(name = "last_name")
    val last_name: String?,
    val status: String?,
    val follow_up_date: String?,
    val record_id: String?,
    val transaction_id: String?,
    val type: String?,
    val get_previous_visit: List<GetPreviousVisit>,
    val updated_at: String?,
    val user_code: String?,
    val user_id: String?,
    val user_name: String?,
    val comment: String?,
    val company_id: String?,
    val created_at: String?,
    val created_by: String?,
    val crm_lead_id: String?,
    val crm_order_id: String?,
    val crm_update_id: String?,
    val customer_id: String?,
    val near_by_location: String?,
    val branch_code: String?,
    val branch_name: String?,
    @ColumnInfo(name = "is_synced")
    val is_synced: String?
    ):Parcelable

{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var Id: Int? = null

    fun getCustomerDetail(): CustomerDetail {
        return CustomerDetail(
            first_name.toString(),
            mobile_phone_number.toString(),
            company_name.toString(),
            address.toString(),
            cnic.toString(),
            occupation.toString(),
            source_of_income.toString(),
            es_income.toString(),
            age.toString(),
            gender.toString(),
            latitude.toString(),
            longitude.toString(),
            product_id.toString(),
            product_name.toString(),
            "",
            lead_status.toString(),
            follow_up_date.toString(),
            comment.toString(),
        )
    }
}

