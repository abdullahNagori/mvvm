package com.example.abl.model.addLead

data class DynamicLeadsResponse(
    val section: String,
    val `data`: List<DynamicLeadsItem>
)