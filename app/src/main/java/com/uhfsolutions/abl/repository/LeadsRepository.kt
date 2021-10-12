package com.uhfsolutions.abl.repository

import com.uhfsolutions.abl.model.addLead.CustomerDetail
import com.uhfsolutions.abl.model.addLead.DynamicLeadsItem
import com.uhfsolutions.abl.model.checkin.CheckinModel
import com.uhfsolutions.abl.model.generic.GenericMsgResponse
import com.uhfsolutions.abl.model.lov.LovResponse
import com.uhfsolutions.abl.model.visitLogs.VisitsCallModel
import com.uhfsolutions.abl.network.Api
import com.uhfsolutions.abl.utils.SharedPrefManager
import retrofit2.Call
import javax.inject.Inject

class LeadsRepository @Inject constructor(
    private val api: Api,
    private val sharedPrefManager: SharedPrefManager
) : BaseRepository() {

    fun addLead(customerDetail: CustomerDetail): Call<DynamicLeadsItem> {
        return api.addLead(customerDetail, "Bearer " + sharedPrefManager.getToken())
    }

    fun addLeadCheckin(checkinModel: CheckinModel): Call<GenericMsgResponse> {
        return api.addLeadCheckin(checkinModel, "Bearer " + sharedPrefManager.getToken())
    }

    suspend fun getLeads(): ArrayList<DynamicLeadsItem> {
        return api.getLeads("Bearer " + sharedPrefManager.getToken())
    }

    fun getVisitCalls(visitsCallModel: VisitsCallModel): Call<ArrayList<CheckinModel>> {
        return api.getVisitsCalls(visitsCallModel,"Bearer " + sharedPrefManager.getToken())
    }

    suspend fun getLovs(): LovResponse {
        return api.getLovs("Bearer " + sharedPrefManager.getToken())
    }
}