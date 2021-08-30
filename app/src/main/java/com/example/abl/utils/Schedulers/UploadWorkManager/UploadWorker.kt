package com.example.abl.utils.Schedulers.UploadWorkManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.abl.constant.Constants
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.GenericMsgResponse
import com.example.abl.network.ApiListener
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import com.example.abl.utils.GsonFactory
import javax.inject.Inject

class UploadWorker @Inject constructor(
    private val userRepository: UserRepository,
    private val daoAccess: DAOAccess,
    var appContext: Context,
    var workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    var apiListener: ApiListener? = null

    override fun doWork(): Result {
        Log.i(TAG, "Fetching Data from Remote host")
        return try {
            val leadData = daoAccess.getUnSyncLeadData()
            //userRepository.apiListener = apiListener
            //val call = userRepository.uploadUserLocation(userList)
            leadData.forEach {
                val call = userRepository.addLead(it.getCustomerDetail())
                val response = GsonFactory.getConfiguredGson() ?.fromJson(call.value, DynamicLeadsItem::class.java)
                if (response?.lead_id != null) {
                    //daoAccess.deleteUserLocation()
                    Log.i("xxDynamicLeadID", response.lead_id.toString())
                    daoAccess.updateLeadStatus(response.lead_id.toString(), it.local_lead_id.toString())
                    daoAccess.updateCheckInLeadStatus(response.lead_id.toString(), it.local_lead_id.toString())
                    Result.success()
                } else {
                    Result.retry()
                }
            }
            Result.retry()


        }

        catch (e: Throwable) {
            e.printStackTrace()
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "Error fetching data", e)
            Result.failure()
        }
    }
    companion object {
        private val TAG = "LocationWorker"
    }

}