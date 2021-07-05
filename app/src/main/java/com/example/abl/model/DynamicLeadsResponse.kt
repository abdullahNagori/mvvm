package com.example.abl.model

data class DynamicLeadsResponse(
    val section: String,
    val `data`: List<DynamicLeadsItem>
)