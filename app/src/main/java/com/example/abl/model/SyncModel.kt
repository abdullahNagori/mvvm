package com.example.abl.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

data class SyncModel(
    var dynamicList: ArrayList<DynamicLeadsItem>?,
    var lovResponse: LovResponse,
    var visitCallResponse: ArrayList<CheckinModel>?,
    var trainingResponse: ArrayList<Training>?,
)