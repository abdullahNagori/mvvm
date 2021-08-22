package com.example.abl.room

import com.example.abl.model.*
import com.example.abl.repository.BaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomHelper @Inject constructor(private val daoAccess: DAOAccess) : BaseRepository() {

    fun insertlead(dynamicLeadsItem: DynamicLeadsItem) {
        CoroutineScope(Dispatchers.IO).launch {
            daoAccess.insertLeadData(dynamicLeadsItem)
        }
    }

    fun insertCheckin(checkinModel: CheckinModel){
        CoroutineScope(Dispatchers.IO).launch {
            daoAccess.insertCheckin(checkinModel)
        }
    }

    fun getLeads(leadStatus: String): List<CustomerDetail>{
        return daoAccess.getAllLeads(leadStatus) as List<CustomerDetail>
    }

    fun getCheckinData(): List<CheckinModel>{
        return daoAccess.getCheckinData() as List<CheckinModel>
    }

    fun insertCompanyProduct(companyProduct: ArrayList<CompanyProduct>){
        daoAccess.insertLovCompanyProduct(companyProduct)
    }
    fun insertVisitStatus(visitStatus: ArrayList<CompanyVisitStatu>){
        daoAccess.insertLovVisitStatus(visitStatus)
    }
}