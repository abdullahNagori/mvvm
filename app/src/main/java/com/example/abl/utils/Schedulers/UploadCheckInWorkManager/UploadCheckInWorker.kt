package com.example.abl.utils.Schedulers.UploadCheckInWorkManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UploadCheckInWorker @Inject constructor(
    private val userRepository: UserRepository,
    private val daoAccess: DAOAccess,
    var appContext: Context,
    var workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val checkInData = daoAccess.getUnSyncedCheckInData("false")

        if (checkInData.isNotEmpty()) {
            checkInData.forEach {
                val callCheckIn = userRepository.addLeadCheckin(it.getCheckInData())
                CoroutineScope(Dispatchers.IO).launch {
                    val response = callCheckIn.execute()
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
        return Result.success()
    }


}


