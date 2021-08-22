package com.example.abl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LovCompanyProduct")
data class CompanyProduct(
    @ColumnInfo(name = "category_name")
    val category_name: String,
    @ColumnInfo(name = "company_id")
    val company_id: String,
    @ColumnInfo(name = "product_code")
    val product_code: String,
    @ColumnInfo(name = "product_name")
    val product_name: String,
    @ColumnInfo(name = "record_id")
    val record_id: String,
    @ColumnInfo(name = "status")
    val status: String,
    val created_at: String,
    val created_by: String,
    val updated_at: String
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var Id: Int? = null
}