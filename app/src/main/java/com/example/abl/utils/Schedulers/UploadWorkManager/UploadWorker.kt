package com.example.abl.utils.Schedulers.UploadWorkManager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.abl.network.ApiListener
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            val checkInData = daoAccess.getUnSyncedCheckInData("false")

            leadData.forEach {
                val callLead = userRepository.addLead(it.getCustomerDetail())
                val response = callLead.execute()
                // val response = GsonFactory.getConfiguredGson() ?.fromJson(dynamicLeadsItem.body().toString(), DynamicLeadsItem::class.java)
                if (response.body()?.lead_id != null) {
                    daoAccess.updateLeadStatus(
                        response.body()?.lead_id.toString(),
                        it.local_lead_id.toString()
                    )
                    daoAccess.updateCheckInLeadStatus(
                        response.body()?.lead_id.toString(),
                        it.local_lead_id.toString()
                    )
                    Result.success()
                } else {
                    Result.retry()
                }
            }

            checkInData.forEach {
                Handler(Looper.getMainLooper()).postDelayed({
                    val callCheckIn = userRepository.addLeadCheckin(it.getCheckInData())
                    // val response = GsonFactory.getConfiguredGson() ?.fromJson(dynamicLeadsItem.body().toString(), DynamicLeadsItem::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        val response =  callCheckIn.execute()
                        if (response.body()?.message == "successful") {
                            daoAccess.uploadCheckInData(it.lead_id)
                            Result.success()
                        } else {
                            Result.retry()
                        }
                    }
                }, 2000)
            }
            Result.success()

        } catch (e: Throwable) {
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