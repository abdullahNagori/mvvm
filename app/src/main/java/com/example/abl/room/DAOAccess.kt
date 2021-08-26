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

    @Query("SELECT * FROM Lead")
    fun getLeadData() : List<DynamicLeadsItem>

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

    @Query("SELECT * FROM PreviousVisits WHERE lead_id IS :lead_id")
    fun getPreviousVisit(lead_id: String): List<GetPreviousVisit>

    @Insert
    fun insertPreviousVisit(previousVisit: GetPreviousVisit)

    @Query("DELETE from PreviousVisits")
    fun deletePreviousVisit()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddLead(leadData: DynamicLeadsItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCheckIn(checkinModel: CheckinModel)

    @Query("SELECT * FROM Checkin")
    fun getCheckInData() : List<CheckinModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)  // onConflict replace the the data when kuch bh happend in inserting
    fun insertLocation(
        userLocation: UserLocation,
    )

}