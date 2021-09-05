package com.example.abl.utils.Schedulers.UploadWorkManager

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.abl.network.ApiListener
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UploadWorker @Inject constructor(
    private val userRepository: UserRepository,
    private val daoAccess: DAOAccess,
    var appContext: Context,
    var workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    var apiListener: ApiListener? = null

    override suspend fun doWork(): Result {
        Log.i(TAG, "Fetching Data from Remote host")
        return try {

            val leadData = daoAccess.getUnSyncLeadData()

            if (leadData.isNotEmpty()) {
                leadData.forEach {
                    val callLead = userRepository.addLead(it.getCustomerDetail())
                    val response = callLead.execute()
                    if (response.body()?.lead_id != null) {
                        daoAccess.updateLeadData(
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
            }

            Handler(Looper.getMainLooper()).postDelayed({
                val checkInData = daoAccess.getUnSyncedCheckInData("false")

                if (checkInData.isNotEmpty()) {
                    checkInData.forEach {
                        val callCheckIn = userRepository.addLeadCheckin(it.getCheckInData())
                        CoroutineScope(Dispatchers.IO).launch {
                            val response =  callCheckIn.execute()
                            if (response.body()?.message == "successful") {
                                daoAccess.updateCheckInStatus(it.lead_id!!)
                                daoAccess.updateLeadStatus(it.lead_id)
                                Result.success()
                            } else {
                                Result.retry()
                            }
                        }
                    }
                }
            }, 5000)

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
        private val TAG = "UploadWorker"
    }
}