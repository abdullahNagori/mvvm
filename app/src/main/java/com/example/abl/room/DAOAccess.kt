package com.example.abl.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abl.model.*

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeadData(dynamicLeadsItem: ArrayList<DynamicLeadsItem>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertCheckin(checkinModel: CheckinModel)
//
    @Query("SELECT * FROM Lead where lead_status_name = :leadStatus")
    fun getLeadData(leadStatus: String) : List<DynamicLeadsItem>
//
//    @Query("SELECT * FROM Checkin")
//    fun getCheckinData() : List<CheckinModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLovCompanyProduct(companyProduct: ArrayList<CompanyProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLovVisitStatus(visitStatus: ArrayList<CompanyVisitStatu>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeadStatus(leadStatus: List<CompanyLeadStatu>)

    @Query("DELETE from LovLeadStatus")
    fun deleteLeadStatus()

    @Query("DELETE from Lead")
    fun deleteLeadData()

    @Query("SELECT * FROM LovLeadStatus")
    fun getLeadStatus() : List<CompanyLeadStatu>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertLeadData(leadData: List<DynamicLeadsItem>)

}