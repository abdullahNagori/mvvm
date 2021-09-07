package com.example.abl.model.sync

import com.example.abl.model.addLead.DynamicLeadsItem
import com.example.abl.model.checkin.CheckinModel
import com.example.abl.model.lov.LovResponse

data class SyncModel(
    var dynamicList: ArrayList<DynamicLeadsItem>?,
    var lovResponse: LovResponse,
    var visitCallResponse: ArrayList<CheckinModel>?
)