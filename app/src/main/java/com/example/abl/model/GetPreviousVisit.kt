package com.example.abl.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(foreignKeys = [ForeignKey(entity = DynamicLeadsItem::class, parentColumns = arrayOf("lead_id"), childColumns = arrayOf("childClassColumn"))])
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
    val visited_time: String,

): Parcelable
{
    @PrimaryKey
    var lead_id: String? = null
}