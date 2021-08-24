package com.example.abl.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "PreviousVisits")
data class GetPreviousVisit(
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "comment")
    val comment: String,
    @ColumnInfo(name = "created_at")
    val created_at: String,
    @ColumnInfo(name = "date_of_conv")
    val date_of_conv: String,
    @ColumnInfo(name = "lead_status_name")
    val lead_status_name: String,
    @ColumnInfo(name = "product_id")
    val product_id: String,
    @ColumnInfo(name = "product_name")
    val product_name: String,
    @ColumnInfo(name = "visit_type")
    val visit_type: String,
    @ColumnInfo(name = "visited_latitude")
    val visited_latitude: String,
    @ColumnInfo(name = "visited_longitude")
    val visited_longitude: String,
    @ColumnInfo(name = "visited_time")
    val visited_time: String,
    @ColumnInfo(name = "lead_id")
    val lead_id: String,

): Parcelable
{
    @PrimaryKey
    (autoGenerate = true)
    var id: Int? = null
}