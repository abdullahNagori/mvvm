package com.example.abl.model

data class SyncModel(
    var dynamicList: ArrayList<DynamicLeadsItem>?,
    var lovResponse: LovResponse,
    var visitCallResponse: ArrayList<VisitsCallResponseItem>?,
)