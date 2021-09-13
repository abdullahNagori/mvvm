package com.example.abl.model.lov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "LovCompanyProducts")
data class CompanyProduct(
    @ColumnInfo(name = "category_name")
    val category_name: String,
    @ColumnInfo(name = "company_id")
    val company_id: String,
    @ColumnInfo(name = "created_at")
    val created_at: String,
    @ColumnInfo(name = "created_by")
    val created_by: String,
    @ColumnInfo(name = "product_code")
    val product_code: String,
    @ColumnInfo(name = "product_name")
    val product_name: String,
    @ColumnInfo(name = "record_id")
    val record_id: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "updated_at")
    val updated_at: String
)

{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var Id: Int? = null
}