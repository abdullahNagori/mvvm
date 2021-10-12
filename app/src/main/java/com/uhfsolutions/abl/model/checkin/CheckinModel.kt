package com.uhfsolutions.abl.model.checkin

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Checkin")
data class CheckinModel(
    @ColumnInfo(name = "customer_name")
    val customer_name: String?,
    @ColumnInfo(name = "account_num")
    val account_num: String?,
    @ColumnInfo(name = "visit_status")
    val visit_status: String?,
    @ColumnInfo(name = "visit_type")
    val visit_type: String?,
    @ColumnInfo(name = "followup_date")
    val followup_date: String?,
    @ColumnInfo(name = "date_of_conv")
    val date_of_conv: String?,
    @ColumnInfo(name = "customer_id")
    val customer_id: String?,
    @ColumnInfo(name = "lead_id")
    val lead_id: String?,
    @ColumnInfo(name = "product_id")
    val product_id: String?,
    @ColumnInfo(name = "product_name")
    val product_name: String?,
    @ColumnInfo(name = "amount")
    val amount: String?,
    @ColumnInfo(name = "comment")
    val comment: String?,
    @ColumnInfo(name = "visited_latitude")
    val visited_latitude: String?,
    @ColumnInfo(name = "visited_longitude")
    val visited_longitude: String?,
    @ColumnInfo(name = "visited_time")
    val visited_time: String?,
    @ColumnInfo(name = "is_synced")
    val is_synced: String?,
    @ColumnInfo(name = "mobile_phone_number")
    val mobile_phone_number: String?,
    @ColumnInfo(name = "user_name")
    val user_name: String?,
    @ColumnInfo(name = "visit_date_time")
    val visit_date_time: String?

    ): Parcelable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null


    fun getCheckInData(): CheckinModel {
        return CheckinModel(
            "",
            account_num,
            visit_status,
            visit_type,
            followup_date,
            date_of_conv,
            customer_id,
            lead_id,
            product_id,
            product_name,
            amount,
            comment,
            visited_latitude,
            visited_longitude,
            "",
            "",
            "",
            "",
            ""
        )
    }
}