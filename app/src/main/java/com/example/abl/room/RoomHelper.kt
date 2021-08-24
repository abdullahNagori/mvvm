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
//
//    fun insertCheckin(checkinModel: CheckinModel) {
//        daoAccess.insertCheckin(checkinModel)
//    }
//
//    fun getCheckinData(): List<CheckinModel> {
//        return daoAccess.getCheckinData() as List<CheckinModel>
//    }

    fun insertCompanyProduct(companyProduct: ArrayList<CompanyProduct>) {
        daoAccess.insertLovCompanyProduct(companyProduct)
    }

    fun insertVisitStatus(visitStatus: ArrayList<CompanyVisitStatu>) {
        daoAccess.insertLovVisitStatus(visitStatus)
    }

    fun insertLeadStatus(leadStatus: List<CompanyLeadStatu>) {
        daoAccess.insertLeadStatus(leadStatus)
    }

    fun deleteLeadStatus() {
        daoAccess.deleteLeadStatus()
    }

    fun deleteLeadData() {
        daoAccess.deleteLeadData()
    }

    fun getLeadsData(): List<DynamicLeadsItem> {
        return daoAccess.getLeadData() as List<DynamicLeadsItem>
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

    fun deletePreviousVisits() {
        daoAccess.deletePreviousVisit()
    }
//    fun insertLeadData(leadData: List<DynamicLeadsItem>) {
//        daoAccess.insertLeadData(leadData)
//    }

}