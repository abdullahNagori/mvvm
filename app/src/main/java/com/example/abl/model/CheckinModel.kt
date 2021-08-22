package com.example.abl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Checkin")
data class CheckinModel(
    @ColumnInfo(name = "customer_name")
    val customer_name: String,
    @ColumnInfo(name = "account_num")
    val account_num: String,
    @ColumnInfo(name = "visit_status")
    val visit_status: String,
    @ColumnInfo(name = "visit_type")
    val visit_type: String,
    @ColumnInfo(name = "followup_date")
    val followup_date: String,
    @ColumnInfo(name = "date_of_conv")
    val date_of_conv: String,
    @ColumnInfo(name = "customer_id")
    val customer_id: String,
    @ColumnInfo(name = "lead_id")
    val lead_id: String,
    @ColumnInfo(name = "product_id")
    val product_id: String,
    @ColumnInfo(name = "product_name")
    val product_name: String,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "comment")
    val comment: String,
    @ColumnInfo(name = "visited_latitude")
    val visited_latitude: String,
    @ColumnInfo(name = "visited_longitude")
    val visited_longitude: String,
    @ColumnInfo(name = "visited_time")
    val visited_time: String
)

{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var Id: Int? = null
}