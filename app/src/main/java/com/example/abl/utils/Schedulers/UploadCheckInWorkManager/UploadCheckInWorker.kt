package com.example.abl.utils.Schedulers.UploadCheckInWorkManager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.abl.repository.LeadsRepository
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import com.example.abl.utils.Schedulers.UploadLeadWorkManager.UploadLeadWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UploadCheckInWorker @Inject constructor(
    private val leadsRepository: LeadsRepository,
    private val daoAccess: DAOAccess,
    var appContext: Context,
    var workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val TAG = "UploadCheckInWorker"
    }

    override suspend fun doWork(): Result {

        return try {
            Log.i(TAG, "Fetching Data from Remote host")

            val checkInData = daoAccess.getUnSyncedCheckInData("false")
            if (checkInData.isNotEmpty()) {
                checkInData.forEach {
                    val response = leadsRepository.addLeadCheckin(it.getCheckInData()).execute()
                    if (response.body() != null) {
                        daoAccess.updateCheckInStatus(it.lead_id!!)
                        daoAccess.updateLeadStatus(it.lead_id)
                        Result.success()
                    } else {
                        Result.retry()
                    }
                }
            }

            return Result.success()

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


