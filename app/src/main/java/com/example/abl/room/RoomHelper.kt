package com.example.abl.room

import android.util.Log
import com.example.abl.model.*
import com.example.abl.repository.BaseRepository
import com.example.abl.utils.GsonFactory
import javax.inject.Inject

class RoomHelper @Inject constructor(private val daoAccess: DAOAccess) : BaseRepository() {

    fun insertLeadData(dynamicLeadsItem: ArrayList<DynamicLeadsItem>) {
        daoAccess.insertLeadData(dynamicLeadsItem)
    }

    fun deleteLeadData() {
        daoAccess.deleteLeadData()
    }

    fun getLeadsData(): List<DynamicLeadsItem> {
        return daoAccess.getUnSyncLeadData() as List<DynamicLeadsItem>
    }

    fun getLeadsStatus(): List<CompanyLeadStatu> {
        return daoAccess.getLeadStatus() as List<CompanyLeadStatu>
    }

    fun getPreviousVisit(lead_id: String): List<GetPreviousVisit> {
        return daoAccess.getPreviousVisit(lead_id) as List<GetPreviousVisit>
    }

    fun insertPreviousVisit(previousVisit: GetPreviousVisit) {
         daoAccess.insertPreviousVisit(previousVisit)
        Log.i("xxInsert", previousVisit.lead_id)
    }

    fun insertAddLead(dynamicLeadsItem: DynamicLeadsItem) {
            daoAccess.insertAddLead(dynamicLeadsItem)
     }

    fun insertCheckIn(checkIn: CheckinModel) {
        daoAccess.insertCheckIn(checkIn)
    }

    fun getCheckIn(): List<CheckinModel> {
        return daoAccess.getCheckInData()
    }

    fun getUserLocation(): List<UserLocation> {
        return daoAccess.getUserLocation()
    }
}