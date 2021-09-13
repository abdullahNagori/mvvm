package com.example.abl.room

import com.example.abl.model.addLead.DynamicLeadsItem
import com.example.abl.model.checkin.CheckinModel
import com.example.abl.model.dashboard.DashboardResponse
import com.example.abl.model.location.UserLocation
import com.example.abl.model.lov.CompanyLeadStatu
import com.example.abl.model.previousVisits.GetPreviousVisit
import com.example.abl.repository.BaseRepository
import javax.inject.Inject

class RoomHelper @Inject constructor(private val daoAccess: DAOAccess, private val ablDatabase: ABLDatabase) : BaseRepository() {

    fun insertLeadData(dynamicLeadsItem: ArrayList<DynamicLeadsItem>) {
        daoAccess.insertLeadData(dynamicLeadsItem)
    }

    fun deleteLeadData() {
        daoAccess.deleteLeadData()
    }

    fun getLeadsData(source: String): List<DynamicLeadsItem> {
        return daoAccess.getLeadData(source)
    }

    fun getLeadsStatus(): List<CompanyLeadStatu> {
        return daoAccess.getLeadStatus() as List<CompanyLeadStatu>
    }

    fun getPreviousVisit(lead_id: String): List<GetPreviousVisit> {
        return daoAccess.getPreviousVisit(lead_id) as List<GetPreviousVisit>
    }

    fun insertPreviousVisit(previousVisit: GetPreviousVisit) {
         daoAccess.insertPreviousVisit(previousVisit)
    }

    fun insertAddLead(dynamicLeadsItem: DynamicLeadsItem) {
        daoAccess.insertAddLead(dynamicLeadsItem)
     }

    fun insertCheckIn(checkIn: CheckinModel) {
        daoAccess.insertCheckIn(checkIn)
    }

    fun insertVisitCallData(checkIn: List<CheckinModel>) {
        daoAccess.insertVisiCallData(checkIn)
    }

    fun getUserLocation(): List<UserLocation> {
        return daoAccess.getUserLocation()
    }

    fun deleteCheckInData(){
         daoAccess.deleteCheckInData()
    }

    fun checkUnSyncLeadData(): List<DynamicLeadsItem> {
        return daoAccess.checkUnSyncedLeadData()
    }

    fun checkUnSyncCheckInData(): List<CheckinModel> {
        return daoAccess.checkUnSyncedCheckInData()
    }

    fun getCheckInCallData(visitType: String): List<CheckinModel> {
        return daoAccess.getCheckInCall(visitType)
    }

    fun getCheckInVisitData(visitType: String): List<CheckinModel> {
        return daoAccess.getCheckInVisit(visitType)
    }

    fun insertDashboardCount(dashboardResponse: DashboardResponse) {
         daoAccess.insertDashBoardCount(dashboardResponse)
    }

    fun deleteDashboardCount(){
        daoAccess.deleteDashBoardCount()
    }

    fun getVisitLogsCount(): String {
        return daoAccess.getVisitLogsCount()
    }

    fun getCallLogsCount(): String {
        return daoAccess.getCallLogsCount()
    }

    fun getFollowupCount(): String {
        return daoAccess.getFollowupCount()
    }

    fun getFollowupData(): List<DynamicLeadsItem> {
        return daoAccess.getFollowupData()
    }

    fun clearDB() {
        ablDatabase.clearAllTables()
    }

//    fun getDashboardCount(): DashboardResponse{
//        return daoAccess.getDashBoardCount()
//    }
}