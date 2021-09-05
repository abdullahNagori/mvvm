package com.example.abl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DashboardCount")
data class DashboardResponse(
    val today_calls: String?,
    val today_followups: String?,
    val today_visits: String?
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int = 0
}