package com.example.abl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LovLeadStatus")
data class CompanyLeadStatu(
    @ColumnInfo(name = "company_id")
    val company_id: String,
    val created_at: String,
    val created_by: String,
    val desc: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "record_id")
    val record_id: String,
    @ColumnInfo(name = "status")
    val status: String,
    val updated_at: String
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var Id: Int? = null
}