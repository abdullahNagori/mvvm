package com.uhfsolutions.abl.model.sync

import com.uhfsolutions.abl.model.addLead.DynamicLeadsItem
import com.uhfsolutions.abl.model.checkin.CheckinModel
import com.uhfsolutions.abl.model.lov.LovResponse

data class SyncModel(
    var dynamicList: ArrayList<DynamicLeadsItem>?,
    var lovResponse: LovResponse,
    var visitCallResponse: ArrayList<CheckinModel>?
)