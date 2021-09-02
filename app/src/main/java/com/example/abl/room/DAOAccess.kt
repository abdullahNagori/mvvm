package com.example.abl.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abl.model.*
import io.reactivex.Flowable

@Dao
interface DAOAccess {


    /** Lead Query */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeadData(dynamicLeadsItem: ArrayList<DynamicLeadsItem>)

    @Query("SELECT * FROM Lead where lead_id = '0'")
    fun getUnSyncLeadData(): List<DynamicLeadsItem>

    @Query("SELECT * FROM Lead ")
    fun getAllLeadData(): List<DynamicLeadsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeadStatus(leadStatus: List<CompanyLeadStatu>)

    @Query("DELETE from Lead")
    fun deleteLeadData()

    @Query("SELECT * FROM LovLeadStatus")
    fun getLeadStatus(): List<CompanyLeadStatu>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddLead(leadData: DynamicLeadsItem)

    @Query("update Lead set lead_id = :leadID where local_lead_id = :localLeadId")
    fun updateLeadData(leadID: String, localLeadId: String)

    @Query("SELECT * FROM Lead WHERE is_synced = 'false'")
    fun checkUnSyncedLeadData(): List<DynamicLeadsItem>

    @Query("update Lead set is_synced= 'true' where lead_id = :leadID")
    fun updateLeadStatus(leadID: String)


    /** CheckIn Query */


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCheckIn(checkinModel: CheckinModel)

    @Query("SELECT * FROM Checkin WHERE is_synced = :is_synced")
    fun getUnSyncedCheckInData(is_synced: String): List<CheckinModel>

    @Query("update Checkin set lead_id = :leadID where lead_id = :localLeadId")
    fun updateCheckInLeadStatus(leadID: String, localLeadId: String)

    @Query("update Checkin set is_synced= 'true' where lead_id = :leadID")
    fun updateCheckInStatus(leadID: String)

    @Query("DELETE from Checkin")
    fun deleteCheckInData()

    @Query("SELECT * FROM Checkin WHERE is_synced = 'false'")
    fun checkUnSyncedCheckInData(): List<CheckinModel>

    @Query("SELECT * FROM Checkin WHERE visit_type = :visitType")
    fun getCheckInCall(visitType: String): List<CheckinModel>

    @Query("SELECT * FROM Checkin WHERE visit_type = :visitType")
    fun getCheckInVisit(visitType: String): List<CheckinModel>


    /** LOV Query */


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLovCompanyProduct(companyProduct: ArrayList<CompanyProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLovVisitStatus(visitStatus: ArrayList<CompanyVisitStatu>)


    /** Previous Visits Query */


    @Query("SELECT * FROM PreviousVisits WHERE lead_id IS :lead_id")
    fun getPreviousVisit(lead_id: String): List<GetPreviousVisit>

    @Insert
    fun insertPreviousVisit(previousVisit: GetPreviousVisit)

    @Query("DELETE from PreviousVisits")
    fun deletePreviousVisit()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVisiCallData(checkinModel: List<CheckinModel>)


    /** Location Service Query */


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation( userLocation: UserLocation)

    @Query("SELECT * FROM UserLocation")
    fun getUserLocation(): List<UserLocation>

    @Query("DELETE from UserLocation")
    fun deleteUserLocation()

}