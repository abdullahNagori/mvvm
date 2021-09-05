package com.example.abl.utils.Schedulers.UploadLeadWorkManager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.abl.network.ApiListener
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import kotlinx.coroutines.*
import javax.inject.Inject

class UploadLeadWorker @Inject constructor(
    private val userRepository: UserRepository,
    private val daoAccess: DAOAccess,
    var appContext: Context,
    var workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

//    var apiListener: ApiListener? = null

    companion object {
        private const val TAG = "UploadWorker"
    }

    override suspend fun doWork(): Result {
        Log.i(TAG, "Fetching Data from Remote host")

        return try {
            val leadData = daoAccess.getUnSyncLeadData()
            if (leadData.isNotEmpty()) {
                leadData.forEach {
                    val response = userRepository.addLead(it.getCustomerDetail()).execute()
                    if (response.body() != null && response.body()?.lead_id != null) {
                        daoAccess.updateLeadData(response.body()?.lead_id.toString(), it.local_lead_id.toString())
                        daoAccess.updateCheckInLeadStatus(response.body()?.lead_id.toString(), it.local_lead_id.toString())
                    } else {
                        Result.retry()
                    }
                }
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
}