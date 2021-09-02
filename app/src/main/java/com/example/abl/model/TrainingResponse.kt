package com.example.abl.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class TrainingResponse(
    val training: List<Training>
)
